package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.connection.ConnectionUtil;
import com.meteorite.core.datasource.db.object.DBLoader;
import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import static org.junit.Assert.*;

public class BaseDBLoaderTest {

    @Test
    public void testGetTable() throws Exception {
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        Connection conn = dataSource.getDbConnection().getConnection();
        try {
            DatabaseMetaData dbMetaData = conn.getMetaData();
//            ResultSet rs = dbMetaData.getExportedKeys("metaui", "metaui", "sys_dz_category");
            ResultSet rs = dbMetaData.getColumns(null, null, "sys_dz_category", "id");
            while (rs.next()) {
                ResultSetMetaData rsMetaData = rs.getMetaData();
                for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                    System.out.println(rsMetaData.getColumnName(i) + " --> " + rs.getString(i));
                }
            }
            rs.close();
        } finally {
            ConnectionUtil.closeConnection(conn);
        }
    }

    @Test
    public void testDropTable() throws Exception {
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        DBLoader loader = dataSource.getDbConnection().getLoader();
        loader.dropTable("sys_layout_prop");
    }

    @Test
    public void testUpdateColumnNullable() throws Exception {
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        DBLoader loader = dataSource.getDbConnection().getLoader();
        loader.updateColumnNullable("sys_meta_field", "default_value", true);
    }

    @Test
    public void testDropForeignKey() throws Exception {
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        DBLoader loader = dataSource.getDbConnection().getLoader();
        loader.dropForeignKey("sys_layout_prop", "FK_layout_prop_layoutId");
    }
}