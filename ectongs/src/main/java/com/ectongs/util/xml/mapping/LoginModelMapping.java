/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.util.xml.mapping;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author wei_jc
 */
public class LoginModelMapping implements XPathMapping {
    public static final Map<String, String> mapping = new HashMap<String, String>();
    
    static {
        mapping.put("schemeId", "/loginInfo/schemeId/text()");
        mapping.put("userId", "/loginInfo/userId/text()");
        mapping.put("userName", "/loginInfo/userName/text()");
        mapping.put("stationId", "/loginInfo/stationId/text()");
        mapping.put("stationAtt", "/loginInfo/stationAtt/text()");
        mapping.put("sessionId", "/loginInfo/sessionId/text()");
        mapping.put("judgeDataRight", "/loginInfo/judgeDataRight/text()");
        mapping.put("dbms", "/loginInfo/dbms/text()");
        mapping.put("serverTime", "/loginInfo/serverTime/text()");
        mapping.put("baseLicenseInfo", "/loginInfo/baseLicenseInfo/text()");
        mapping.put("loginStatCode", "/loginInfo/returnCode/text()");
        mapping.put("returnErrorMsg", "/loginInfo/returnErrorMsg/text()");
    }
    
    
    @Override
    public Map<String, String> getMapping() {
        return mapping;
    }
}
