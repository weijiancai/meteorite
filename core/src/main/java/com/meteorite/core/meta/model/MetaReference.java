package com.meteorite.core.meta.model;

/**
 * 元数据引用
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MetaReference {
    private String id;
    private Meta pkMeta;
    private MetaField pkMetaField;
    private Meta fkMeta;
    private MetaField fkMetaField;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Meta getPkMeta() {
        return pkMeta;
    }

    public void setPkMeta(Meta pkMeta) {
        this.pkMeta = pkMeta;
    }

    public MetaField getPkMetaField() {
        return pkMetaField;
    }

    public void setPkMetaField(MetaField pkMetaField) {
        this.pkMetaField = pkMetaField;
    }

    public Meta getFkMeta() {
        return fkMeta;
    }

    public void setFkMeta(Meta fkMeta) {
        this.fkMeta = fkMeta;
    }

    public MetaField getFkMetaField() {
        return fkMetaField;
    }

    public void setFkMetaField(MetaField fkMetaField) {
        this.fkMetaField = fkMetaField;
    }
}
