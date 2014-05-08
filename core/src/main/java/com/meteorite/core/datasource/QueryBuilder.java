package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.sql.SqlBuilder;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.meta.model.Meta;
import com.meteorite.core.util.UObject;
import com.meteorite.fxbase.ui.component.form.ICanQuery;

import java.util.List;

/**
 * 查询条件语句生成器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class QueryBuilder {
    private SqlBuilder sql;
    private Meta meta;

    private QueryBuilder(Meta meta) {
        this.meta = meta;
        sql = SqlBuilder.create();
    }

    public static QueryBuilder create(Meta meta) {
        return new QueryBuilder(meta);
    }

    public QueryBuilder add(String colName, QueryModel queryModel, Object value, MetaDataType dataType, boolean isAnd) {
        sql.add(colName, queryModel, value, dataType, isAnd);

        return this;
    }

    public QueryBuilder add(String colName, Object value, boolean isAnd) {
        sql.add(colName, QueryModel.EQUAL, value, MetaDataType.STRING, isAnd);
        return this;
    }

    public QueryBuilder add(String colName, Object value) {
        sql.add(colName, QueryModel.EQUAL, value, MetaDataType.STRING, true);
        return this;
    }

    public SqlBuilder sql() {
        return sql;
    }

    public Meta getMeta() {
        return meta;
    }
}
