package com.meteorite.core.util;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class UCharset {
    public static Set<String> getUtfChinese() {
        Set<String> set = new HashSet<String>();
        for (int i = '\u4e00'; i < '\u9fbf'; i++) {
            System.out.println((char)i);
            set.add("" + (char)i);
        }
        return set;
    }
}
