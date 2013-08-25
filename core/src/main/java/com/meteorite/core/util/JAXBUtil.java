package com.meteorite.core.util;

import com.meteorite.core.util.jaxb.ListBean;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.List;

/**
 * @author wei_jc
 */
public class JAXBUtil {
    public static String marshalToString(Object obj, Class<?>... classes) throws Exception {
        StringWriter sw = new StringWriter();
        getMarshaller(obj, classes).marshal(obj, sw);

        return sw.toString();
    }
    
    public static void marshalToFile(Object obj, File file, Class<?>... classes) throws Exception {
        getMarshaller(obj, classes).marshal(obj, file);
    }

    public static <T> String marshalListToString(List<T> list, Class<?> classes) throws Exception {
        ListBean<T> listBean = new ListBean<T>(list);

        return marshalToString(listBean, classes);
    }

    private static Marshaller getMarshaller(Object obj, Class<?>... classes) throws JAXBException {
        Class<?>[] cs = getClasses(obj.getClass(), classes);
        JAXBContext context = JAXBContext.newInstance(cs);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        return marshaller;
    }

    private static Class<?>[] getClasses(Class<?> clazz, Class<?>[] classes) {
        int count = 1;
        if (classes != null) {
            count += classes.length;
        }
        Class<?>[] cs = new Class[count];
        cs[0] = clazz;
        if (classes != null) {
            System.arraycopy(classes, 0, cs, 1, classes.length);
        }
        return cs;
    }

    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(String xmlStr, Class<T> clazz) throws Exception {
        return (T)getUnmarshaller(clazz).unmarshal(new StringReader(xmlStr));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(Class<T> clazz) throws Exception {
        return (T)getUnmarshaller(clazz).unmarshal(new File(new File(JAXBUtil.class.getResource("/").toURI()), clazz.getSimpleName() + ".xml"));
    }
    
    @SuppressWarnings("unchecked")
    public static <T> T unmarshal(File file, Class<T> clazz) throws Exception {
        return (T)getUnmarshaller(clazz).unmarshal(file);
    }

    public static <T> T unmarshal(InputStream inputStream, Class<T> clazz) throws JAXBException {
        return (T)getUnmarshaller(clazz).unmarshal(inputStream);
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> unmarshalList(String xmlStr, Class<?>... classes) throws Exception {
        ListBean<T> listBean = (ListBean<T>) getUnmarshaller(classes).unmarshal(new StringReader(xmlStr));

        return listBean.getList();
    }

    private static Unmarshaller getUnmarshaller(Class<?>... classes) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(getClasses(ListBean.class, classes));
        return context.createUnmarshaller();
    }
}
