package com.meteorite.core.config;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 参数配置
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class ProfileSetting {
    /** 配置类型 */
    private String confSection;
    /** 属性名 */
    private String confKey;
    /** 属性值 */
    private String confValue;
    /** 排序号 */
    private int sortNum;
    /** 备注 */
    private String memo;

    public ProfileSetting() {
    }

    public ProfileSetting(String confSection, String confKey, String confValue) {
        this.confSection = confSection;
        this.confKey = confKey;
        this.confValue = confValue;
    }

    @XmlAttribute
    public String getConfSection() {
        return confSection;
    }

    public void setConfSection(String confSection) {
        this.confSection = confSection;
    }

    @XmlAttribute
    public String getConfKey() {
        return confKey;
    }

    public void setConfKey(String confKey) {
        this.confKey = confKey;
    }

    @XmlAttribute
    public String getConfValue() {
        return confValue;
    }

    public void setConfValue(String confValue) {
        this.confValue = confValue;
    }

    @XmlAttribute
    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    @XmlAttribute
    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

}
