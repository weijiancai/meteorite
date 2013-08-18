package com.meteorite.fxbase.ui.valuectl;

import java.io.Serializable;
import java.util.Date;

/**
 * @author weijiancai
 */
public class DictCode implements Serializable {
    private String id;
    private String name;
    private String value;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private DictCategory category;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }

    public int getSortNum() {
        return sortNum;
    }

    public void setSortNum(int sortNum) {
        this.sortNum = sortNum;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public DictCategory getCategory() {
        return category;
    }

    public void setCategory(DictCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return name;
    }
}
