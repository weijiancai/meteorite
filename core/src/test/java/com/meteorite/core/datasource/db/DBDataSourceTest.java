package com.meteorite.core.datasource.db;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.request.ExpDbDdlRequest;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBDataSourceTest {

    @Test
    public void testFindResourceByPath() throws Exception {
        String path = "/table/sys_db_version";
        DataSource dataSource = DataSourceManager.getSysDataSource();
        dataSource.findResourceByPath(path);
    }

    @Test
    public void testExpDdl() throws Exception {
        ExpDbDdlRequest request = new ExpDbDdlRequest();
        request.setExpDbType(DatabaseType.HSQLDB);
        request.setSaveFilePath("D:\\workspace\\meteorite\\core\\src\\main\\resources\\db_upgrade\\1.0.0\\metaui_hsqldb.sql");
        DBDataSource dataSource = DataSourceManager.getSysDataSource();
        dataSource.expDdl(request);
    }
}