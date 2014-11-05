package com.ectongs.dsconfig;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.JdbcDrivers;
import junit.framework.TestCase;

public class DsConfigExportTest extends TestCase {

    public void testExport() throws Exception {
        DBDataSource target = new DBDataSource("ectb2b", JdbcDrivers.SQL_SERVER, "jdbc:sqlserver://192.168.3.3:1433;DatabaseName=ectb2b", "sa", "7758521", null);
        DBDataSource source = new DBDataSource("yhebp", JdbcDrivers.MYSQL, "jdbc:mysql://localhost:3306/yhebp", "root", "root", null);

        DsConfigExport export = new DsConfigExport(source, target);
        export.export("BAS_Send", "YwPx_Basu");
    }

    public void testExport1() throws Exception {
        DBDataSource source = new DBDataSource("yhbis", JdbcDrivers.MYSQL, "jdbc:sqlserver://192.168.0.105:1433;databaseName=yhbis", "sa", "7758521", null);

        DsConfigExport export = new DsConfigExport(source, source);
        export.export("Task", "PackTask");
    }
}