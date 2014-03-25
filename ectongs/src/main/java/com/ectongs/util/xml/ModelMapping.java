/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.util.xml;

import cc.csdn.base.util.UtilString;
import com.ectongs.http.HttpAccepter;
import com.ectongs.http.HttpAccepterMapping;
import com.ectongs.http.LoginModel;
import com.ectongs.util.xml.mapping.LoginModelMapping;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

//import com.yhsms.edi.client.login.LoginModel;

/**
 *
 * @author wei_jc
 */
public class ModelMapping {
    private static final Logger log = Logger.getLogger(ModelMapping.class.getName());
    private static Map<Class<?>, Map<String, String>> modelMapping;
    
    static {
        modelMapping = new HashMap<Class<?>, Map<String, String>>();
        modelMapping.put(HttpAccepter.class, HttpAccepterMapping.mapping);
        modelMapping.put(LoginModel.class, LoginModelMapping.mapping);
        
    }
    
    public static <T> T getModel(String xmlContent, Class<T> clazz) {
        T t = null;
        try {
            t = clazz.newInstance();
            
            if(UtilString.isEmpty(xmlContent)) return t;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlContent)));

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xpath = xPathFactory.newXPath();
            XPathExpression expression;
            Class<?> paramType;
            Field field;
            Method method;
            String value;
            for (Map.Entry<String, String> entry : modelMapping.get(clazz).entrySet()) {
                expression = xpath.compile(entry.getValue());
                try {
                    field = clazz.getDeclaredField(entry.getKey());
                } catch (Exception e) {
                    log.warning(e.getMessage());
                    continue;
                }
                if(null != field) {
                    paramType = field.getType();
                    value = expression.evaluate(document);
                    try {
                        method = clazz.getMethod("set" + firstCharToUpper(entry.getKey()), paramType);
                    } catch(Exception e) {
                        log.warning(e.getMessage());
                        continue;
                    }
                    if(!UtilString.isEmpty(value)) {
                        if (paramType == String.class) {
                            method.invoke(t, value);
                        } else if (paramType == int.class || paramType == Integer.class) {
                            method.invoke(t, Integer.parseInt(value));
                        } else if(paramType == float.class || paramType == Float.class) {
                            method.invoke(t, Float.parseFloat(value));
                        } else if(paramType == double.class || paramType == Double.class) {
                            method.invoke(t, Double.parseDouble(value));
                        } else if(paramType == long.class || paramType == Long.class) {
                            method.invoke(t, Long.parseLong(value));
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return t;
    }
    
    private static String firstCharToUpper(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
