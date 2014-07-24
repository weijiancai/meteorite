package com.meteorite.core.util;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class GenDocbook {
    public static void main(String[] args) throws TransformerException {
        File xml = new File("D:\\workspace_temp\\TestDocbook\\src\\金尚网上商城.xml");
        File xsl = new File("D:\\workspace_temp\\TestDocbook\\docbook-xsl-1.74.0\\html\\docbook.xsl");
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer(new StreamSource(xsl));
        transformer.transform(new StreamSource(xml), new StreamResult(new File("D:\\workspace_temp\\TestDocbook\\doc\\金尚网上商城1.html")));
    }
}
