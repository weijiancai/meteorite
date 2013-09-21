package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @version 1.0
 */
public class SystemManagerTest {
    @Test
    public void testGetTaobaoProjectConfig() throws Exception {
        ProjectConfig taobao = SystemManager.getInstance().createProjectConfig(".taobao");
        assertThat(taobao.getDataSources().size(), equalTo(1));
    }

    @Test
    public void testAddDataSource() throws Exception {
        ProjectConfig projConf = SystemManager.getInstance().createProjectConfig(".taobao");
        DataSource dataSource = new DataSource("taobao", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "", "1.0.0");
        projConf.getDataSources().add(dataSource);
        SystemManager.getInstance().save(projConf);
    }
}
