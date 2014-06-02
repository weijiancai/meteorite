package com.meteorite.core.util;

import com.sun.xml.internal.messaging.saaj.packaging.mime.util.ASCIIUtility;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UStringTest {
    @Test
    public void testTableNameToClassName() throws Exception {

    }

    @Test public void testLeftPadding() {
        String expect = "0000001";
        String actual = UString.leftPadding("1", '0', 7);
        assertThat(actual, equalTo(expect));

        expect = "1234567";
        actual = UString.leftPadding("1234567", '0', 7);
        assertThat(actual, equalTo(expect));
    }

    @Test public void testCharset() throws UnsupportedEncodingException {
        String str = "魏";
        System.out.println(str);
        for (byte b : str.getBytes("GBK")) {
//            System.out.print(Integer.toHexString(b));
            System.out.print(Integer.toHexString(0xff & b));
        }
        System.out.println();
//        System.out.println(str.getBytes());
        System.out.println("\u4E00");
        System.out.println("\u9FBF");
        for (int i = 0; i < 100; i++) {

        }

        int i = 0x4e00;
        str = "\\u" + Integer.toHexString(i);
        System.out.println(str);
        str = "4e00";
        byte[] bytes = new byte[str.length()];
        for (int j = 0; j < str.length(); j++) {
            bytes[j] = (byte) str.charAt(j);
        }

        System.out.println(new String(bytes));
        System.out.println("\u7528\\u6237\\u540d\\u4e0d\\u80fd\\u4e3a\\u7a7a");
//        System.out.println(Integer.);
    }

    @Test
    public void testGetLastName() {
        String str = "META-INF/MANIFEST.MF";
        assertThat(UString.getLastName(str, "/"), equalTo("MANIFEST.MF"));
        str = "com/";
        assertThat(UString.getLastName(str, "/"), equalTo("com"));
        str = "com/meteorite/core/config/";
        assertThat(UString.getLastName(str, "/"), equalTo("config"));
        str = "com/meteorite/core/config/ProjectConfig.class";
        assertThat(UString.getLastName(str, "/"), equalTo("ProjectConfig.class"));
    }
}
