package com.meteorite.core.config;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class SystemNavigationTest {
    @Test
    public void testSystemNav() throws Exception {
        SystemManager.getInstance().init(SystemManager.SystemType.DESKTOP);
        SystemNavigation nav = new SystemNavigation();
        System.out.println(JSON.toJSONString(nav, SerializerFeature.PrettyFormat));
    }
}
