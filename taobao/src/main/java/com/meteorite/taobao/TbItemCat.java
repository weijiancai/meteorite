package com.meteorite.taobao;

import com.meteorite.core.meta.annotation.MetaElement;
import com.meteorite.core.meta.annotation.MetaFieldElement;

/**
 * 淘宝商品类目结构
 *
 * @author wei_jc
 * @version 1.0.0
 */
@MetaElement(displayName = "淘宝商品类目结构")
public class TbItemCat {
    /**
     * 商品所属类目ID
     */
    private long cid;
    /**
     * 父类目ID=0时，代表的是一级的类目
     */
    private long parentCid;
    /**
     * 类目名称
     */
    private String name;
    /**
     * 该类目是否为父类目(即：该类目是否还有子类目)
     */
    private boolean isParent;
    /**
     * 状态。可选值:normal(正常),deleted(删除)
     */
    private String status;
    /**
     * 排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
     */
    private int sortOrder;

    @MetaFieldElement(displayName = "商品所属类目ID")
    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    @MetaFieldElement(displayName = "父类目ID")
    public long getParentCid() {
        return parentCid;
    }

    public void setParentCid(long parentCid) {
        this.parentCid = parentCid;
    }

    @MetaFieldElement(displayName = "类目名称")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @MetaFieldElement(displayName = "该类目是否为父类目")
    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        this.isParent = parent;
    }

    @MetaFieldElement(displayName = "状态")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @MetaFieldElement(displayName = "排列序号")
    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }
}
