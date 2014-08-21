package com.meteorite.core.datasource.db.object.loader;

import com.meteorite.core.datasource.DataSource;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.object.impl.DBConnectionImpl;
import org.junit.Test;

import static org.junit.Assert.*;

public class BaseDBLoaderTest {

    @Test
    public void testGetTable() throws Exception {
        DataSource dataSource = DataSourceManager.getSysDataSource();
    }
}