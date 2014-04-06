package com.meteorite.core.datasource.persist;

import com.meteorite.core.datasource.db.object.DBColumn;
import com.meteorite.core.dict.DictCategory;
import com.meteorite.core.dict.DictCode;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.ui.model.*;
import com.meteorite.core.util.UString;
import com.meteorite.core.util.UUIDUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaPDBFactory {
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
                map.put("desc", meta.getDesc());
                map.put("is_valid", meta.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", meta.getSortNum());

                result.put("sys_meta", map);

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
                map.put("desc", field.getDesc());
                map.put("is_valid", field.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", field.getSortNum());
                DBColumn column = field.getColumn();
                if (column != null) {
                    map.put("db_column", column.getFullName());
                }

                result.put("sys_meta_field", map);

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
                map.put("desc", category.getDesc());
                map.put("is_system", category.isSystem() ? "T" : "F");
                map.put("is_valid", category.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", category.getSortNum());

                result.put("sys_dz_category", map);

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
                map.put("desc", code.getDesc());
                map.put("is_valid", code.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", code.getSortNum());

                result.put("sys_dz_code", map);

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
                map.put("desc", layout.getDesc());
                map.put("ref_id", layout.getRefId());
                map.put("is_valid", layout.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", layout.getSortNum());

                result.put("sys_layout", map);

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
                map.put("layout_id", property.getLayout().getId());
                map.put("name", property.getName());
                map.put("display_name", property.getDisplayName());
                map.put("default_value", property.getDefaultValue());
                map.put("prop_type", property.getPropType().name());
                map.put("desc", property.getDesc());
                map.put("sort_num", property.getSortNum());

                result.put("sys_layout_prop", map);

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
                map.put("desc", view.getDesc());
                map.put("is_valid", view.isValid() ? "T" : "F");
                map.put("input_date", new Date());
                map.put("sort_num", view.getSortNum());

                result.put("sys_view", map);

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

                result.put("sys_view_layout", map);

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

                result.put("sys_view_config", map);

                return result;
            }
        };
    }
}
