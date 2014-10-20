package com.meteorite.core.datasource.persist;

import com.meteorite.core.config.ProfileSetting;
import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaItem;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.project.NavMenu;
import com.meteorite.core.project.ProjectDefine;
import com.meteorite.core.project.tpl.CodeTpl;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaPDBFactory {
    public static IPDB getDataSource(final DataSource dataSource) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(dataSource.getId())) {
                    dataSource.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", dataSource.getId());
                map.put("name", dataSource.getName());
                map.put("display_name", dataSource.getDisplayName());
                map.put("description", dataSource.getDescription());
                map.put("is_valid", dataSource.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", dataSource.getSortNum());
                map.put("host", dataSource.getHost());
                map.put("port", dataSource.getPort());
                map.put("user_name", dataSource.getUserName());
                map.put("pwd", dataSource.getPwd());
                map.put("type", dataSource.getType().name());

                result.put("mu_db_datasource", map);

                return result;
            }
        };
    }

    public static IPDB getProfileSetting(final ProfileSetting setting) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("conf_section", setting.getConfSection());
                map.put("conf_key", setting.getConfKey());
                map.put("conf_value", setting.getConfValue());
                map.put("sort_num", setting.getSortNum());
                map.put("memo", setting.getMemo());

                result.put("mu_profile_setting", map);

                return result;
            }
        };
    }

    public static IPDB getMeta(final Meta meta) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(meta.getId())) {
                    meta.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", meta.getId());
                map.put("name", meta.getName());
                map.put("display_name", meta.getDisplayName());
                map.put("description", meta.getDescription());
                map.put("is_valid", meta.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", meta.getSortNum());
                if (meta.getResource() != null) {
                    map.put("rs_id", meta.getResource().getId());
                }

                result.put("mu_meta", map);

                return result;
            }
        };
    }

    public static IPDB getMetaReference(final MetaReference metaRef) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(metaRef.getId())) {
                    metaRef.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", metaRef.getId());
                if (metaRef.getPkMeta() != null) {
                    map.put("pk_meta_id", metaRef.getPkMeta().getId());
                } else {
                    map.put("pk_meta_id", metaRef.getPkMetaId());
                }
                if (metaRef.getPkMetaField() != null) {
                    map.put("pk_meta_field_id", metaRef.getPkMetaField().getId());
                } else {
                    map.put("pk_meta_field_id", metaRef.getPkMetaFieldId());
                }
                if (metaRef.getFkMeta() != null) {
                    map.put("fk_meta_id", metaRef.getFkMeta().getId());
                } else {
                    map.put("fk_meta_id", metaRef.getFkMetaId());
                }
                if (metaRef.getFkMetaField() != null) {
                    map.put("fk_meta_field_id", metaRef.getFkMetaField().getId());
                } else {
                    map.put("fk_meta_field_id", metaRef.getFkMetaFieldId());
                }

                result.put("mu_meta_reference", map);

                return result;
            }
        };
    }

    public static IPDB getMetaField(final MetaField field) {
        return new IPDB() {

            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(field.getId())) {
                    field.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                if (field.getMeta() == null) {
                    map.put("meta_id", field.getMetaId());
                } else {
                    map.put("meta_id", field.getMeta().getId());
                }
                map.put("id", field.getId());
                map.put("name", field.getName());
                map.put("display_name", field.getDisplayName());
                map.put("data_type", field.getDataType().name());
                map.put("description", field.getDescription());
                map.put("dict_id", field.getDictId());
                map.put("is_valid", field.isValid() ? "T" : "F");
                map.put("default_value", field.getDefaultValue());
                map.put("input_date", new Date());
                map.put("sort_num", field.getSortNum());
                map.put("original_name", field.getOriginalName());
                map.put("max_length", field.getMaxLength());
                map.put("is_pk", field.isPk() ? "T" : "F");
                map.put("is_fk", field.isFk() ? "T" : "F");
                map.put("is_require", field.isRequire() ? "T" : "F");

                result.put("mu_meta_field", map);

                return result;
            }
        };
    }

    public static IPDB getMetaItem(final MetaItem item) {
        return new IPDB() {

            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(item.getId())) {
                    item.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                map.put("display_name", item.getDisplayName());
                map.put("data_type", item.getDataType().name());
                map.put("description", item.getDescription());
                map.put("category", item.getCategory());
                map.put("is_valid", item.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", item.getSortNum());
                map.put("max_length", item.getMaxLength());


                result.put("mu_meta_item", map);

                return result;
            }
        };
    }

    public static IPDB getMetaSql(final Meta meta) {
        return new IPDB() {
            @Override
            public Map<String, ? extends Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("meta_id", meta.getId());
                map.put("sql_text", meta.getSqlText());

                result.put("mu_meta_sql", map);

                return result;
            }
        };
    }

    public static IPDB getDictCategory(final DictCategory category) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", category.getId());
                map.put("name", category.getName());
                map.put("description", category.getDescription());
                map.put("pid", category.getPid());
                map.put("is_system", category.isSystem() ? "T" : "F");
                map.put("is_valid", category.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", category.getSortNum());

                result.put("mu_dz_category", map);

                return result;
            }
        };
    }

    public static IPDB getDictCode(final DictCode code) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(code.getId())) {
                    code.setId(UUIDUtil.getUUID());
                }

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                if (code.getCategory() != null) {
                    map.put("category_id", code.getCategory().getId());
                } else {
                    map.put("category_id", code.getCategoryId());
                }
                map.put("id", code.getId());
                map.put("name", code.getName());
                map.put("display_name", code.getDisplayName());
                map.put("description", code.getDescription());
                map.put("is_valid", code.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", code.getSortNum());

                result.put("mu_dz_code", map);

                return result;
            }
        };
    }

    public static IPDB getLayout(final Layout layout) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", layout.getId());
                if (layout.getParent() != null) {
                    map.put("pid", layout.getParent().getId());
                }
                map.put("name", layout.getName());
                map.put("display_name", layout.getDisplayName());
                map.put("description", layout.getDescription());
                map.put("ref_id", layout.getRefId());
                map.put("is_valid", layout.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", layout.getSortNum());

                result.put("mu_layout", map);

                return result;
            }
        };
    }

    public static IPDB getLayoutProperty(final LayoutProperty property) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", property.getId());
                map.put("layout_type", property.getLayoutType().name());
                if (property.getLayout() != null) {
                    map.put("layout_id", property.getLayout().getId());
                }
                map.put("name", property.getName());
                map.put("display_name", property.getDisplayName());
                map.put("default_value", property.getDefaultValue());
                map.put("prop_type", property.getPropType().name());
                map.put("description", property.getDescription());
                map.put("sort_num", property.getSortNum());

                result.put("mu_layout_prop", map);

                return result;
            }
        };
    }

    public static IPDB getView(final View view) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", view.getId());
                map.put("name", view.getName());
                map.put("display_name", view.getDisplayName());
                map.put("description", view.getDescription());
                if (view.getMeta() == null) {
                    map.put("meta_id", view.getMetaId());
                } else {
                    map.put("meta_id", view.getMeta().getId());
                }
                map.put("is_valid", view.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", view.getSortNum());

                result.put("mu_view", map);

                return result;
            }
        };
    }

    public static IPDB getViewLayout(final ViewLayout viewLayout) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", viewLayout.getId());
                map.put("view_id", viewLayout.getView().getId());
                map.put("layout_id", viewLayout.getLayout().getId());
                if (viewLayout.getMeta() != null) {
                    map.put("meta_id", viewLayout.getMeta().getId());
                }

                result.put("mu_view_layout", map);

                return result;
            }
        };
    }

    public static IPDB getViewConfig(final ViewConfig config) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", config.getId());
                map.put("view_layout_id", config.getViewLayout().getId());
                map.put("prop_id", config.getProperty().getId());
                if (config.getField() != null) {
                    map.put("meta_field_id", config.getField().getId());
                }
                map.put("value", config.getValue());

                result.put("mu_view_config", map);

                return result;
            }
        };
    }

    public static IPDB getViewProperty(final ViewProperty property) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                if (UString.isEmpty(property.getId())) {
                    property.setId(UUIDUtil.getUUID());
                }
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", property.getId());
                if (property.getView() != null) {
                    map.put("view_id", property.getView().getId());
                } else {
                    map.put("view_id", property.getViewId());
                }
                if (property.getProperty() != null) {
                    map.put("layout_prop_id", property.getProperty().getId());
                } else {
                    map.put("layout_prop_id", property.getLayoutPropId());
                }
                if (property.getField() != null) {
                    map.put("meta_field_id", property.getField().getId());
                } else {
                    map.put("meta_field_id", property.getMetaFieldId());
                }
                map.put("value", property.getValue());

                result.put("mu_view_prop", map);

                return result;
            }
        };
    }

    public static IPDB getProjectDefine(final ProjectDefine projectDefine) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("id", UString.getValue(projectDefine.getId(), UUIDUtil.getUUID()));
                map.put("name", projectDefine.getName());
                map.put("display_name", projectDefine.getDisplayName());
                map.put("description", projectDefine.getDescription());
                map.put("package_name", projectDefine.getPackageName());
                map.put("is_valid", projectDefine.isValid() ? "T" : "F");
                map.put("sort_num", projectDefine.getSortNum());
                map.put("input_date", projectDefine.getInputDate());
                map.put("project_url", projectDefine.getProjectUrl());

                result.put("mu_project_define", map);

                return result;
            }
        };
    }

    public static IPDB getNavMenu(final NavMenu navMenu) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("id", UString.getValue(navMenu.getId(), UUIDUtil.getUUID()));
                map.put("name", navMenu.getName());
                map.put("display_name", navMenu.getDisplayName());
                map.put("icon", navMenu.getIcon());
                map.put("url", navMenu.getUrl());
                map.put("pid", navMenu.getPid());
                map.put("level", navMenu.getLevel());
                map.put("project_id", navMenu.getProjectId());
                map.put("is_valid", navMenu.isValid() ? "T" : "F");
                map.put("sort_num", navMenu.getSortNum());
                map.put("input_date", navMenu.getInputDate());

                result.put("mu_nav_menu", map);

                return result;
            }
        };
    }

    public static IPDB getCodeTpl(final CodeTpl codeTpl) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();

                map.put("id", UString.getValue(codeTpl.getId(), UUIDUtil.getUUID()));
                map.put("name", codeTpl.getName());
                map.put("display_name", codeTpl.getDisplayName());
                map.put("description", codeTpl.getDescription());
                map.put("project_id", codeTpl.getProjectId());
                map.put("file_name", codeTpl.getFileName());
                map.put("file_path", codeTpl.getFilePath());
                map.put("tpl_file", codeTpl.getTplFile());
                map.put("tpl_content", codeTpl.getTplContent());
                map.put("is_valid", codeTpl.isValid() ? "T" : "F");
                map.put("sort_num", codeTpl.getSortNum());
                map.put("input_date", codeTpl.getInputDate());

                result.put("mu_code_tpl", map);

                return result;
            }
        };
    }
}
