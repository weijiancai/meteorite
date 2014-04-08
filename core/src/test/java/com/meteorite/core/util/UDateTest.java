package com.meteorite.core.util;

import junit.framework.TestCase;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UDateTest extends TestCase {
    public void testToLocalDate() throws Exception {
        String date = "2012-03-01 10:55:23";
        System.out.println(UDate.toLocalDate(date));
    }
}
