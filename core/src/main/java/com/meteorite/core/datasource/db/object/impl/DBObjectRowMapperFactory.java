package com.meteorite.core.datasource.db.object.impl;

import com.meteorite.core.datasource.db.RowMapper;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.DBTable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author weijiancai
 * @version 1.0.0
 */
public class DBObjectRowMapperFactory {
    public static RowMapper<DBSchema> getDBSchema() {
        return new RowMapper<DBSchema>() {
            @Override
            public DBSchema mapRow(ResultSet rs) throws SQLException {
                DBSchemaImpl schema = new DBSchemaImpl();
                schema.setName(rs.getString("SCHEMA_NAME"));
                return schema;
            }
        };
    }

    public static RowMapper<DBTable> getDBTable() {
        return new RowMapper<DBTable>() {
            @Override
            public DBTable mapRow(ResultSet rs) throws SQLException {
                DBTableImpl table = new DBTableImpl();
                table.setName(rs.getString("TABLE_NAME"));
                table.setComment(rs.getString("TABLE_COMMENT"));
                return table;
            }
        };
    }
}
