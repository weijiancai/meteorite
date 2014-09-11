package com.meteorite.core.datasource.request;

import java.util.HashMap;
import java.util.Map;

/**
 * 导出数据请求
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class ExpDataRequest extends BaseRequest {
    public ExpDataRequest(String path) {
        super(path);
    }

    private String[] excludeColumns;
    private Map<String, Object> defaultValues = new HashMap<String, Object>();

    public String[] getExcludeColumns() {
        return excludeColumns;
    }

    public void setExcludeColumns(String[] excludeColumns) {
        this.excludeColumns = excludeColumns;
    }

    public Map<String, Object> getDefaultValues() {
        return defaultValues;
    }

    public void setDefaultValues(Map<String, Object> defaultValues) {
        this.defaultValues = defaultValues;
    }

    public void addDefaultValue(String key, String value) {
        defaultValues.put(key, value);
    }
}
