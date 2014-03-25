/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.http;

import com.ectongs.util.xml.mapping.XPathMapping;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wei_jc
 */
public class HttpAccepterMapping implements XPathMapping {
    public static final Map<String, String> mapping = new HashMap<String, String>();

    static {
        mapping.put("returnCode", "//returnData/code/text()");
        mapping.put("message", "/returnData/message/text()");
        mapping.put("error", "/returnData/error/text()");
        mapping.put("data", "/returnData/data/text()");
    }
    @Override
    public Map<String, String> getMapping() {
        return mapping;
    }
}
