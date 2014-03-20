package com.meteorite.core.dict;

import com.meteorite.core.datasource.db.DatabaseType;
import com.meteorite.core.meta.DisplayStyle;
import com.meteorite.core.util.jaxb.JAXBUtil;
import org.junit.Test;

import java.util.List;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class DictManagerTest {
    @Test
    public void testAddDict() throws Exception {
        DictManager.addDict(DatabaseType.class);
    }

    @Test
    public void testAddEnumDict() {
        DictCategory dict = DictManager.getDict(DisplayStyle.class);
        System.out.println(dict);
    }
}
