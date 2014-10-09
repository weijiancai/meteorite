package com.meteorite.core.meta.model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * 元数据引用
 *
 * @author wei_jc
 * @since 1.0.0
 */
@XmlRootElement
public class MetaReference {
    /** 引用ID */
    private String id;
    /** 主元数据ID */
    private String pkMetaId;
    /** 主元数据列ID */
    private String pkMetaFieldId;
    /** 引用元数据ID */
    private String fkMetaId;
    /** 引用元数据列ID */
    private String fkMetaFieldId;

    private Meta pkMeta;
    private MetaField pkMetaField;
    private Meta fkMeta;
    private MetaField fkMetaField;

    @XmlAttribute
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute
    public String getPkMetaId() {
        return pkMetaId;
    }

    public void setPkMetaId(String pkMetaId) {
        this.pkMetaId = pkMetaId;
    }

    @XmlAttribute
    public String getPkMetaFieldId() {
        return pkMetaFieldId;
    }

    public void setPkMetaFieldId(String pkMetaFieldId) {
        this.pkMetaFieldId = pkMetaFieldId;
    }

    @XmlAttribute
    public String getFkMetaId() {
        return fkMetaId;
    }

    public void setFkMetaId(String fkMetaId) {
        this.fkMetaId = fkMetaId;
    }

    @XmlAttribute
    public String getFkMetaFieldId() {
        return fkMetaFieldId;
    }

    public void setFkMetaFieldId(String fkMetaFieldId) {
        this.fkMetaFieldId = fkMetaFieldId;
    }

    @XmlTransient
    public Meta getPkMeta() {
        return pkMeta;
    }

    public void setPkMeta(Meta pkMeta) {
        this.pkMeta = pkMeta;
    }

    @XmlTransient
    public MetaField getPkMetaField() {
        return pkMetaField;
    }

    public void setPkMetaField(MetaField pkMetaField) {
        this.pkMetaField = pkMetaField;
    }

    @XmlTransient
    public Meta getFkMeta() {
        return fkMeta;
    }

    public void setFkMeta(Meta fkMeta) {
        this.fkMeta = fkMeta;
    }

    @XmlTransient
    public MetaField getFkMetaField() {
        return fkMetaField;
    }

    public void setFkMetaField(MetaField fkMetaField) {
        this.fkMetaField = fkMetaField;
    }
}
