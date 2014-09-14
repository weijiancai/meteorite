package com.meteorite.core.util.dom;

import com.meteorite.core.datasource.DataMap;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class DomUtilTest {

    @Test
    public void testToListMap() throws Exception {
        String str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<config>\n" +
                "    <row meta=\"Datasource\" fieldName=\"type\" dict=\"DataSourceType\"/>\n" +
                "</config>";
        File file = new File("D:\\workspace\\meteorite\\core\\src\\main\\resources\\config\\MetaFieldConfig.xml");
        List<DataMap> list = DomUtil.toListMap(file, "/config/row");
        System.out.println(list);
    }
}