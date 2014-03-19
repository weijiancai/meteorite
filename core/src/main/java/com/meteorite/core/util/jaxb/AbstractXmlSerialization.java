package com.meteorite.core.util.jaxb;

import com.meteorite.core.util.UObject;

import java.io.File;

/**
 * XML序列化抽象类，使用JAXB将类序列化为xml文件，或从xml文件中生成类对象
 *
 * @author wei_jc
 * @since 1.0.0
 */
public abstract class AbstractXmlSerialization {
    /**
     * 将要序列化和反序列化的XML文件
     *
     * @return 返回XML文件
     */
    protected abstract File getXmlFile();

    /**
     * 从xml文件中生成类对象
     */
    public void load() throws Exception {
        File file = getXmlFile();
        if (file.exists()) {
            Object obj = JAXBUtil.unmarshal(file, this.getClass());
            UObject.clone(obj, this);
        }
    }

    /**
     * 将类对象序列化为xml文件
     *
     * @throws Exception
     */
    public void store() throws Exception {
        JAXBUtil.marshalToFile(this, getXmlFile());
    }
}
