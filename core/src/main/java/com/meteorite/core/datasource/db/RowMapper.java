package com.meteorite.core.datasource.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author weijiancai
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
