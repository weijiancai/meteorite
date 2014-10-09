package com.meteorite.core.backup;

import com.meteorite.core.config.PathManager;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.dict.DictManager;
import com.meteorite.core.meta.MetaManager;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.project.ProjectManager;
import com.meteorite.core.util.UDate;
import com.meteorite.core.util.UFile;
import com.meteorite.core.util.jaxb.JAXBUtil;

import java.io.File;
import java.util.Date;

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

    public void backup() throws Exception {
        BackupInfo info = new BackupInfo();
        // 备份用户数据字典
        info.setDictList(DictManager.getUserDictList());
        // 备份项目
        info.setProjectList(ProjectManager.getProjects());

        File file = UFile.createFile(PathManager.getBackupPath(), "backup" + UDate.dateToString(new Date(), "yyyyMMddHHmmss") + ".xml");
        JAXBUtil.marshalToFile(info, file, BackupInfo.class, DictCategory.class, DictCode.class);
        String str = JAXBUtil.marshalToString(info, BackupInfo.class, DictCategory.class, DictCode.class);
        System.out.println(str);
    }

    public void restore() throws Exception {

    }

    private File getLastBackupFile() {
        File backupDir = PathManager.getBackupPath();

        for (File file : backupDir.listFiles()) {

        }

        return null;
    }
}
