package com.meteorite.core.rest;

import com.meteorite.core.datasource.DataMap;
import com.meteorite.core.datasource.QueryCondition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Rest Request
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class Request {
    private String path;
    private Map<String, String> params;
    private PathHandler pathHandler;
    private List<QueryCondition> conditions;
    private List<DataMap> listData;
    private ExpRequest expRequest;

    public Request(String path) {
        this.path = path;
        pathHandler = new PathHandler(path);
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public PathHandler getPathHandler() {
        return pathHandler;
    }

    public List<QueryCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryCondition> conditions) {
        this.conditions = conditions;
    }

    public List<DataMap> getListData() {
        return listData;
    }

    public void setListData(List<DataMap> listData) {
        this.listData = listData;
    }

    public ExpRequest getExpRequest() {
        if (expRequest == null) {
            expRequest = new ExpRequest();
        }
        return expRequest;
    }

    public class ExpRequest {
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
}
