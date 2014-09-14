package com.meteorite.core.util.jaxb;

import com.meteorite.core.datasource.DataMap;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JAXBUtilTest {
    @Test
    public void testMarshalListToString() throws Exception {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Map<String, String> data = new HashMap<String, String>();
        data.put("meta", "Datasource");
        data.put("fieldName", "type");
        list.add(data);

        ListBean listBean = new ListBean();

        String str = JAXBUtil.marshalListToString(list, HashMap.class);
        System.out.println(str);
    }

    @Test
    public void test() throws JAXBException {
        Map<String, String> data = new HashMap<String, String>();
        data.put("meta", "Datasource");
        data.put("fieldName", "type");

        JAXBContext context = JAXBContext.newInstance(MyHashMapType.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        marshaller.marshal(new JAXBElement(new QName("root"),MyHashMapType.class,new MyHashMapType(data)),System.out);
    }
}