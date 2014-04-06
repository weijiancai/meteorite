package com.ectongs.dsconfig;

import cc.csdn.base.util.UtilBase64;
import com.meteorite.core.util.UBase64;
import org.junit.Test;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class Base64Test {
    @Test
    public void test() {
        String str = "NDQxMTFfT1NHQQ==";
        System.out.println(new String(UtilBase64.decode(str)));
        System.out.println(new String(UtilBase64.decode("MzI5ODE4NV9PU0dB")));
        System.out.println(new String(UtilBase64.decode("MTk2NjY1N19PU0dB")));
        System.out.println(new String(UtilBase64.decode("MzMyNDc1M19PU0dB")));
        System.out.println(UBase64.decode("MzMyNDc1M19PU0dB"));

        System.out.println(UtilBase64.encode("3325394_OSGA".getBytes()));
        System.out.println(UBase64.encode("3328916_OSGA"));
    }

}
