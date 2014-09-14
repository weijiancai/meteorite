/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.meteorite.core.util.dom;

import com.meteorite.core.datasource.DataMap;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.hsqldb.lib.StringInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * XML dom处理工具类
 *
 * @author wei_jc
 */
public class DomUtil {
    public static List<DataMap> toListMap(String xmlStr, String rowXpath) throws Exception {
        return toListMap(new StringInputStream(xmlStr), rowXpath);
    }

    public static List<DataMap> toListMap(File xmlFile, String rowXpath) throws Exception {
        return toListMap(new FileInputStream(xmlFile), rowXpath);
    }

    public static List<DataMap> toListMap(InputStream is, String rowXpath) throws Exception {
        SAXReader reader = new SAXReader();
        Document doc = reader.read(is);
        List list = doc.selectNodes(rowXpath);
        List<DataMap> result = new ArrayList<DataMap>();
        for (Object object : list) {
            Element element = (Element) object;
            DataMap data = new DataMap();
            for(Iterator it=element.attributeIterator();it.hasNext();){
                Attribute attribute = (Attribute) it.next();
                String attrName = attribute.getName();
                String attrValue = attribute.getValue();

                data.put(attrName, attrValue);
            }
            result.add(data);
        }

        return result;
    }
}
