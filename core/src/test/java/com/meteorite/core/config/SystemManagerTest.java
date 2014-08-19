package com.meteorite.core.config;

import com.meteorite.core.datasource.db.DBDataSource;
import com.meteorite.core.datasource.db.DatabaseType;
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
        DBDataSource dataSource = new DBDataSource("taobao", "org.hsqldb.jdbcDriver", "jdbc:hsqldb:hsql://localhost/taobao", "sa", "", "1.0.0");
        projConf.getDataSources().add(dataSource);
        SystemManager.save(projConf);
    }

    @Test
    public void testLoadProject() throws Exception {
        ProjectConfig projectConfig = SystemManager.getInstance().getProjectConfig(".taobao");
        assertThat(projectConfig.getName(), equalTo(".taobao"));
        assertThat(projectConfig.getDataSources().size(), equalTo(1));
        DBDataSource dataSource = projectConfig.getDataSources().get(0);
        assertThat(dataSource.getName(), equalTo("taobao"));
        assertThat(dataSource.getDriverClass(), equalTo("org.hsqldb.jdbcDriver"));
        assertThat(dataSource.getUrl(), equalTo("jdbc:hsqldb:hsql://localhost/taobao"));
        assertThat(dataSource.getUsername(), equalTo("sa"));
        assertThat(dataSource.getPassword(), equalTo(""));
        assertThat(dataSource.getDbVersion(), equalTo("1.0.0"));
        assertThat(dataSource.getDatabaseType(), equalTo(DatabaseType.UNKNOWN));
    }
}
