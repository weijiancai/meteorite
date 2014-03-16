package com.meteorite.core.datasource.db;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public interface RowMapper<T> {
    T mapRow(ResultSet rs) throws SQLException;
}
