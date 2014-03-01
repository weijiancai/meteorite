package com.meteorite.core.util;

import com.meteorite.core.datasource.db.DatabaseType;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UClassTest {
    @Test
    public void testToString() throws Exception {
        System.out.println(UClass.toString(DatabaseType.class));
    }
}
