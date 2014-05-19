package com.meteorite.core.util;

import org.junit.Test;

import java.util.Set;

public class UCharsetTest {
    @Test public void utfChinese() {
        Set<String> set = UCharset.getUtfChinese();
        for (String str : set) {
            System.out.println(str);
        }
    }
}