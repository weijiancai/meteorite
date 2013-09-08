package com.meteorite.core.ui.config;

import com.meteorite.core.meta.MetaDataType;
import com.meteorite.core.ui.ILayoutProperty;
import com.meteorite.core.ui.model.Layout;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * 布局属性信息
 *
 * @author wei_jc
 * @version 1.0.0
 */
@XmlRootElement
public class LayoutProperty implements ILayoutProperty {
    /** 属性ID */
    private int id;
    /** 属性名称 */
    private String name;
    /** 属性显示名 */
    private String displayName;
    /** 默认值 */
    private String defaultValue;
    /** 属性值 */
    private String value;
    /** 数据类型 */
    private MetaDataType dataType;
    /** 排序号*/
    private int sortNum;


    public LayoutProperty(String name, String displayName, String value, MetaDataType dataType, String defaultValue) {
        this.dataType = dataType;
        this.defaultValue = defaultValue;
        this.displayName = displayName;
        this.name = name;
        this.value = value;
    }

    public void setDataType(MetaDataType dataType) {
        this.dataType = dataType;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getDefaultValue() {
        return defaultValue;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public MetaDataType getDataType() {
        return dataType;
    }

    @Override
    public int getSortNum() {
        return sortNum;
    }
}
