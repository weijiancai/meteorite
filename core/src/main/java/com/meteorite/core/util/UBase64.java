package com.meteorite.core.util;

import java.util.Base64;

/**
 * Base64编码工具类
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class UBase64 {
    /**
     * 字节数组做Base64编码
     *
     * @param bytes 字节数组
     * @return 返回Base64编码字符串
     */
    public static String encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 字符串做Base64编码
     *
     * @param str 字符串
     * @return 返回Base64编码字符串
     */
    public static String encode(String str) {
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 字符串做Base64解码
     *
     * @param str 字符串
     * @return 返回Base64解码后的字符串
     */
    public static String decode(String str) {
        return new String(Base64.getDecoder().decode(str));
    }
}
