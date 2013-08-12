package com.meteorite.core.config;

import com.meteorite.core.db.DataSource;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @version 1.0
 */
public class ProjectConfigFactoryTest {
    @Test
    public void testGetTaobaoProjectConfig() throws Exception {
        ProjectConfig taobao = ProjectConfigFactory.getProjectConfig(".taobao");
        assertThat(taobao.getDataSources().size(), equalTo(1));
    }

    @Test
    public void testAddDataSource() throws Exception {
        ProjectConfig projConf = ProjectConfigFactory.getProjectConfig(".taobao");
        DataSource dataSource = new DataSource("taobao", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "");
        projConf.getDataSources().add(dataSource);
        ProjectConfigFactory.save(projConf);
    }
}
