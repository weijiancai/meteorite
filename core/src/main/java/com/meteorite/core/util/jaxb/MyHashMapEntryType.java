package com.meteorite.core.util.jaxb;

import javax.xml.bind.annotation.XmlAttribute;
import java.util.Map;

/**
 * @author wei_jc
 * @since 1.0.0
 */
public class MyHashMapEntryType {
    @XmlAttribute // @XmlElement and @XmlValue are also fine
    public String key;

    @XmlAttribute // @XmlElement and @XmlValue are also fine
    public String value;

    public MyHashMapEntryType() {}

    public MyHashMapEntryType(Map.Entry<String,String> e) {
        key = e.getKey();
        value = e.getValue();
    }
}
