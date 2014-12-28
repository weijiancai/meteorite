package com.meteorite.core.backup;

import com.meteorite.core.config.PathManager;
import com.meteorite.core.config.ProfileSetting;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.DefaultDataSource;
import com.meteorite.core.datasource.db.util.JdbcTemplate;
import com.meteorite.core.datasource.persist.MetaPDBFactory;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaItem;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.project.NavMenu;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.ProjectManager;
import com.meteorite.core.project.tpl.CodeTpl;
import com.meteorite.core.ui.ViewManager;
import com.meteorite.core.ui.model.View;
import com.meteorite.core.ui.model.ViewProperty;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 备份管理
 *
 * @author wei_jc
 * @version 1.0.0
 */
public class BackupManager {
    private static BackupManager ourInstance = new BackupManager();

    public static BackupManager getInstance() {
        return ourInstance;
    }

    private BackupManager() {
    }

    public void backup(BackupSetting setting) throws Exception {
        BackupInfo info = new BackupInfo();
        // 备份系统参数设置
        if (setting.isSetting()) {
            info.setSettingList(SystemManager.getSettingList());
        }
        // 备份用户数据字典
        if (setting.isDict()) {
            info.setDictCategory(DictManager.getRoot());
        }
        // 备份项目
        if (setting.isProject()) {
            info.setProjectList(ProjectManager.getProjects());
        }
        // 备份元数据项
        if (setting.isMetaItem()) {
            info.setMetaItemList(MetaManager.getMetaItemList());
        }
        // 备份元数据
        if (setting.isMeta()) {
            info.setMetaList(MetaManager.getMetaList());
        }
        // 备份元数据引用
        if (setting.isMetaReference()) {
            info.setMetaReferenceList(MetaManager.getMetaReferenceList());
        }
        // 备份视图
        if (setting.isView()) {
            info.setViewList(ViewManager.getViewList());
        }
        // 备份数据源
        if (setting.isDataSource()) {
            info.setDataSourceList(DataSourceManager.getDataSources());
        }

        File file = UFile.createFile(PathManager.getBackupPath(), "backup" + UDate.dateToString(new Date(), "yyyyMMddHHmmss") + ".xml");
        saveToFile(file);
    }

    public void backupSystemSetting() throws Exception {
        BackupInfo info = new BackupInfo();
        // 备份元数据
        info.setMetaList(MetaManager.getSystemMetaList());
        info.setMetaReferenceList(MetaManager.getSystemMetaReferenceList());
        // 备份视图
        info.setViewList(ViewManager.getSystemViewList());

    }

    public void saveToFile(File file) throws Exception {
        if (file != null) {
            JAXBUtil.marshalToFile(this, file, BackupInfo.class, DictCategory.class, DictCode.class, ProjectDefine.class, NavMenu.class, CodeTpl.class, Meta.class, MetaField.class, MetaItem.class);
        }
    }

    public void restore(BackupSetting setting, File file) throws Exception {
        if (file == null) {
            file = getLastBackupFile();
        }
        if (file == null) {
            return;
        }
        BackupInfo info = JAXBUtil.unmarshal(file, BackupInfo.class);
        JdbcTemplate template = new JdbcTemplate();
        try {
            // 恢复系统参数
            if (setting.isSetting()) {
                template.clearTable("mu_profile_setting");
                for (ProfileSetting profileSetting : info.getSettingList()) {
                    template.save(MetaPDBFactory.getProfileSetting(profileSetting));
                }
            }
            // 恢复数据字典
            if (setting.isDict()) {
                template.clearTable("mu_dz_category");
                restoreDict(info.getDictCategory(), template);
            }
            // 恢复元数据项
            if (setting.isMetaItem()) {
                template.clearTable("mu_meta_item");
                for (MetaItem item : info.getMetaItemList()) {
                    template.save(MetaPDBFactory.getMetaItem(item));
                }
            }
            // 恢复元数据
            if (setting.isMeta()) {
                template.clearTable("mu_meta");
                for (Meta meta : info.getMetaList()) {
                    template.save(MetaPDBFactory.getMeta(meta));
                    for (MetaField field : meta.getFields()) {
                        template.save(MetaPDBFactory.getMetaField(field));
                    }
                    // 恢复MetaSql
                    if (UString.isNotEmpty(meta.getSqlText())) {
                        template.save(MetaPDBFactory.getMetaSql(meta));
                    }
                }
            }
            // 恢复元数据引用
            if (setting.isMetaReference()) {
                template.clearTable("mu_meta_reference");
                for (MetaReference reference : info.getMetaReferenceList()) {
                    template.save(MetaPDBFactory.getMetaReference(reference));
                }
            }
            // 恢复视图
            if (setting.isView()) {
                template.clearTable("mu_view");
                for (View view : info.getViewList()) {
                    template.save(MetaPDBFactory.getView(view));
                    for (ViewProperty property : view.getViewProperties()) {
                        template.save(MetaPDBFactory.getViewProperty(property));
                    }
                    ViewManager.addCache(view);
                }
            }
            // 恢复数据源
            if (setting.isDataSource()) {
                template.clearTable("mu_db_datasource");
                for (DataSource dataSource : info.getDataSourceList()) {
                    if ("MetaUI_DataSource".equals(dataSource.getId())) {
                        continue;
                    }
                    template.save(MetaPDBFactory.getDataSource(dataSource));
                    DataSourceManager.addDataSource(dataSource);
                }
            }
            // 恢复项目
            if (setting.isProject()) {
                template.clearTable("mu_project_define");
                for (ProjectDefine project : info.getProjectList()) {
                    template.save(MetaPDBFactory.getProjectDefine(project));
                    // 恢复导航菜单
                    restoreNavMenu(project.getRootNavMenu(), template);
                    // 恢复代码模板
                    for (CodeTpl codeTpl : project.getCodeTpls()) {
                        template.save(MetaPDBFactory.getCodeTpl(codeTpl));
                    }
                    ProjectManager.putCache(project);
                }
            }

            template.commit();
        } finally {
            template.close();
        }
    }

    private void restoreDict(DictCategory parent, JdbcTemplate template) throws Exception {
        if (parent.getChildren().size() > 0) {
            for (DictCategory category : parent.getChildren()) {
                restoreDict(category, template);
            }
        }

        if ("ROOT".equals(parent.getId()) || "System_Category".equals(parent.getId())) {
            return;
        }

        template.save(MetaPDBFactory.getDictCategory(parent));
        DictManager.addDict(parent);
        for (DictCode code : parent.getCodeList()) {
            template.save(MetaPDBFactory.getDictCode(code));
        }
    }

    private void restoreNavMenu(NavMenu parent, JdbcTemplate template) throws Exception {
        if (parent.getChildren().size() > 0) {
            for (NavMenu category : parent.getChildren()) {
                restoreNavMenu(category, template);
            }
        }

        if ("ROOT".equalsIgnoreCase(parent.getId())) {
            return;
        }

        template.save(MetaPDBFactory.getNavMenu(parent));
    }

    private File getLastBackupFile() {
        File backupDir = PathManager.getBackupPath();
        File[] files = backupDir.listFiles();
        if (files == null) {
            return null;
        }
        if (files.length == 1) {
            return files[0];
        }

        File lastFile = files[0];
        for (File file : files) {
            if (file.lastModified() > lastFile.lastModified()) {
                lastFile = file;
            }
        }

        return lastFile;
    }
}
