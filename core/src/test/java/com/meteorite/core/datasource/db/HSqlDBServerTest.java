package com.meteorite.core.datasource.db;

import com.meteorite.core.util.HSqlDBServer;
import org.junit.Test;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class HSqlDBServerTest {
    @Test
    public void testStart() throws Exception {
        HSqlDBServer.getInstance().addDbFile("sys", "C:\\Users\\wei_jc\\.meteorite\\hsqldb\\sys");
        HSqlDBServer.getInstance().start();
        Thread.sleep(500000);
    }
}
