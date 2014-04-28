package com.meteorite.core.util;

import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UBase64Test {
    @Test
    public void test() {
        String str = "NDQxMTFfT1NHQQ==";
        System.out.println(UBase64.decode("MzMyNDc1M19PU0dB"));

        System.out.println(UBase64.encode("3330209_OSGA"));
    }

}
