package com.meteorite.core.datasource.db;

import com.meteorite.core.config.SystemConfig;
import com.meteorite.core.config.SystemManager;
import com.meteorite.core.datasource.db.object.DBConnection;
import com.meteorite.core.datasource.db.object.DBSchema;
import com.meteorite.core.datasource.db.object.impl.DBTableImpl;
import com.meteorite.core.util.jaxb.JAXBUtil;
import org.junit.Test;

import static junit.framework.Assert.assertNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DBManagerTest {
    @Test
    public void testGetConnection() throws Exception {
        SystemManager.getInstance();
        DBConnection conn = DBManager.getConnection(SystemConfig.SYS_DB_NAME);
        System.out.println(conn);
        assertNotNull(conn);
        assertThat(conn.getDatabaseType(), equalTo(DatabaseType.HSQLDB));
        DBSchema schema = conn.getSchema();
        String str = JAXBUtil.marshalToString(schema, DBTableImpl.class);
        System.out.println(str);
    }
}
