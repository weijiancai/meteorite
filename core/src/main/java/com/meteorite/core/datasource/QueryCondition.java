package com.meteorite.core.datasource;

import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;

/**
 * 查询条件
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class QueryCondition {
    private String colName;
    private QueryModel queryModel;
    private Object value;
    private MetaDataType dataType;
    private boolean isAnd;

    public QueryCondition(String colName, QueryModel queryModel, Object value, MetaDataType dataType, boolean isAnd) {
        this.colName = colName;
        this.queryModel = queryModel;
        this.value = value;
        this.dataType = dataType;
        this.isAnd = isAnd;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public QueryModel getQueryModel() {
        return queryModel;
    }

    public void setQueryModel(QueryModel queryModel) {
        this.queryModel = queryModel;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public MetaDataType getDataType() {
        return dataType;
    }

    public boolean isAnd() {
        return isAnd;
    }

    public void setAnd(boolean isAnd) {
        this.isAnd = isAnd;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public static QueryCondition create(String colName, QueryModel queryModel, Object value, MetaDataType dataType, boolean isAnd) {
        return new QueryCondition(colName, queryModel, value, dataType, isAnd);
    }

    public static QueryCondition create(String colName, QueryModel queryModel, Object value) {
        return new QueryCondition(colName, queryModel, value, MetaDataType.STRING, true);
    }

    public static QueryCondition create(String colName, Object value) {
        return new QueryCondition(colName, QueryModel.EQUAL, value, MetaDataType.STRING, true);
    }
}
