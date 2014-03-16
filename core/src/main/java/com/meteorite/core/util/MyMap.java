package com.meteorite.core.util;

import java.util.HashMap;

/**
 * 对HashMap的一些扩展
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MyMap<K,V> extends HashMap<K,V> {
    /**
     * 获得key对应的字符串值
     *
     * @param key Key
     * @return 返回key对应的字符串值
     */
    public String getString(Object key) {
        return UObject.toString(get(key));
    }
}
