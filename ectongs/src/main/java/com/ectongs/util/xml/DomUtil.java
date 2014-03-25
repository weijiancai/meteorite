/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.util.xml;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.StringReader;

/**
 *
 * @author wei_jc
 */
public class DomUtil {
    public static Document parse(String xmlStr) {
        try {
            if(null == xmlStr) return null;
            
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(xmlStr)));
            
            return document;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
    
    public static String getValue(Document doc, String xpath) {
        XPathFactory factory = XPathFactory.newInstance();
        XPath xPath = factory.newXPath();
        try {
            XPathExpression expression = xPath.compile(xpath);
            return expression.evaluate(doc);
        } catch (XPathExpressionException ex) {
            ex.printStackTrace();
        }
        
        return null;
    }
}
