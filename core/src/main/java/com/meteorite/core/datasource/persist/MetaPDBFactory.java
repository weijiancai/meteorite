package com.meteorite.core.datasource.persist;

import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.meta.model.MetaField;
import com.meteorite.core.util.UUIDUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

                result.put("sys_meta_field", map);

                return result;
            }
        };
    }
}
