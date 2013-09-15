package com.meteorite.core.ui.layout;

import com.meteorite.core.ui.config.LayoutConfig;
import com.meteorite.core.util.JAXBUtil;
import org.junit.Test;

/**
 * @author wei_jc
 * @version 1.0.0
 */
public class LayoutInitTest {
    @Test
    public void testGetRoot() throws Exception {
        LayoutConfig config = LayoutInit.getRoot();
        String xml = JAXBUtil.marshalToString(config, LayoutConfig.class);
        System.out.println(xml);
    }
}
