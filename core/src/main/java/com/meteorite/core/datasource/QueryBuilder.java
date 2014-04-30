package com.meteorite.core.datasource;

import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.datasource.db.sql.SqlBuilder;
import com.meteorite.core.dict.QueryModel;
import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.util.UObject;
import com.meteorite.core.util.UString;

/**
 * 查询条件语句生成器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class QueryBuilder {
    private SqlBuilder sql;

    private QueryBuilder(DatabaseType dbType) {
        sql = SqlBuilder.create(dbType);
    }

    public static QueryBuilder create(DatabaseType dbType) {
        return new QueryBuilder(dbType);
    }

    public QueryBuilder add(String colName, QueryModel queryModel, Object value, MetaDataType dataType) {
        if (UObject.isEmpty(value)) {
            return this;
        }

        String model = " = ";

        switch (queryModel) {
            case EQUAL: {
                model = " = ";
                break;
            }
            case NOT_EQUAL: {
                model = " != ";
                break;
            }
            case LESS_THAN: {
                model = " < ";
                break;
            }
            case LESS_EQUAL: {
                model = " <= ";
                break;
            }
            case GREATER_THAN: {
                model = " > ";
                break;
            }
            case GREATER_EQUAL: {
                model = " >= ";
                break;
            }
            case LIKE: {
                sql.andLike(colName, "%%%s%%", UObject.toString(value));
                return this;
            }
            case LEFT_LIKE: {
                sql.andLike(colName, "%s%%", UObject.toString(value));
                return this;
            }
            case RIGHT_LIKE: {
                sql.andLike(colName, "%%%s", UObject.toString(value));
                return this;
            }
        }

        if (MetaDataType.DATE == dataType) {
            sql.andDate(colName + model, UObject.toString(value));
        } else {
            sql.and(colName + model, value);
        }

        return this;
    }

    public SqlBuilder sql() {
        return sql;
    }
}
