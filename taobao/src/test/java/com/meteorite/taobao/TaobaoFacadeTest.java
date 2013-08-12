package com.meteorite.taobao;

import com.meteorite.core.config.ProjectConfig;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class TaobaoFacadeTest {
    @Test
    public void testGetProjectConfig() throws Exception {
        TaobaoFacade facade = TaobaoFacade.getInstance();
        ProjectConfig projectConfig = facade.getProjectConfig();
        assertThat(projectConfig.getDataSources().size(), equalTo(2));
    }
}
