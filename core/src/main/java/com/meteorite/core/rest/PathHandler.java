package com.meteorite.core.rest;

import com.meteorite.core.util.UString;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 路径处理器
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class PathHandler {
    private static List<String> DB_KEYS = Arrays.asList("schema", "table", "column", "columns");
    private String path;

    public PathHandler(String path) {
        this.path = path;
    }

    public Map<String, String> parseForDb() {
        Map<String, String> result = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        String key = null;
        for(String str : path.split("/")) {
            if (UString.isEmpty(str)) {
                continue;
            }
            if(DB_KEYS.contains(str)) {
                if (UString.isNotEmpty(key)) {
                    result.put(key, sb.toString());
                    sb.delete(0, sb.length());
                }

                key = str;
            } else {
                sb.append(str);
            }
        }
        // 最后一个
        if (!result.containsKey(key)) {
            result.put(key, sb.toString());
        }

        return result;
    }
}
