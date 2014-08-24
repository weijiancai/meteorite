package com.meteorite.core.datasource.persist;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.meta.model.MetaReference;
import com.meteorite.core.ui.model.*;
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
                dataSource.setId(UUIDUtil.getUUID());

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

                result.put("mu_db_datasource", map);

                return result;
            }
        };
    }

    public static IPDB getMeta(final Meta meta) {
        return new IPDB() {
            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                meta.setId(UUIDUtil.getUUID());

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", meta.getId());
                map.put("name", meta.getName());
                map.put("display_name", meta.getDisplayName());
                map.put("description", meta.getDescription());
                map.put("is_valid", meta.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", meta.getSortNum());
                if (meta.getDataSource() != null) {
                    map.put("ds_name", meta.getDataSource().getName());
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
                metaRef.setId(UUIDUtil.getUUID());

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", metaRef.getId());
                map.put("pk_meta_id", metaRef.getPkMeta().getId());
                map.put("pk_meta_field_id", metaRef.getPkMetaField().getId());
                map.put("fk_meta_id", metaRef.getFkMeta().getId());
                map.put("fk_meta_field_id", metaRef.getFkMetaField().getId());

                result.put("mu_meta_reference", map);

                return result;
            }
        };
    }

    public static IPDB getMetaField(final MetaField field) {
        return new IPDB() {

            @Override
            public Map<String, Map<String, Object>> getPDBMap() {
                field.setId(UUIDUtil.getUUID());

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("meta_id", field.getMeta().getId());
                map.put("id", field.getId());
                map.put("name", field.getName());
                map.put("display_name", field.getDisplayName());
                map.put("data_type", field.getDataType().name());
                map.put("description", field.getDescription());
                map.put("is_valid", field.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", field.getSortNum());
                DBColumn column = field.getColumn();
                if (column != null) {
                    map.put("db_column", column.getFullName());
                }
                map.put("dict_id", field.getDictId());

                result.put("mu_meta_field", map);

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
                code.setId(UUIDUtil.getUUID());

                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("category_id", code.getCategory().getId());
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
                map.put("meta_id", view.getMeta().getId());
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
                Map<String, Map<String, Object>> result = new HashMap<String, Map<String, Object>>();

                Map<String, Object> map = new HashMap<String, Object>();
                map.put("id", UUIDUtil.getUUID());
                map.put("view_id", property.getView().getId());
                map.put("layout_prop_id", property.getProperty().getId());
                if (property.getField() != null) {
                    map.put("meta_field_id", property.getField().getId());
                }
                map.put("value", property.getValue());

                result.put("mu_view_prop", map);

                return result;
            }
        };
    }
}
