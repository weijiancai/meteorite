package com.meteorite.core.db.object.impl;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;

/**
 * @author wei_jc
 * @version 0.0.1
 */
public class DBResultSet<T> {
    private ResultSet rs;
    private ResultSetMetaData metaData;

    public DBResultSet(ResultSet rs) throws SQLException {
        this.rs = rs;
        this.metaData = rs.getMetaData();
    }

    public List<T> getAllData() throws SQLException {

        while (rs.next()) {
            for (int i = 1; i <= metaData.getColumnCount(); i++) {

            }
        }
        return null;
    }
}
