package com.meteorite.core.datasource.db.object;

import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.DataSourceManager;
import com.meteorite.core.datasource.db.DatabaseType;
import org.junit.Assert;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class DBTableTest {
    @Test
    public void testTable() throws Exception {
        SystemManager.getInstance().init();
        DBConnection conn = DataSourceManager.getSysDataSource().getDbConnection();
        assertNotNull(conn);
        assertThat(conn.getDatabaseType(), equalTo(DatabaseType.HSQLDB));
        DBSchema schema = conn.getSchema();
        DBTable table = schema.getTable("sys_dz_code");
        Assert.assertNotNull(table);
        DBColumn idCol = table.getColumn("id");
        assertTrue(idCol.isPk());
        DBColumn categoryCol = table.getColumn("category_id");
        assertFalse(categoryCol.isPk());
        DBColumn nameCol = table.getColumn("name");
        assertFalse(nameCol.isPk());
    }
}