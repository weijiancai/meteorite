package com.meteorite.fxbase.ui.valuectl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author weijiancai
 */
public class DictCategory implements Serializable {
    private String id;
    private String name;
    private boolean isValid;
    private int sortNum;
    private Date inputDate;

    private List<DictCode> codeList;

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

    public List<DictCode> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<DictCode> codeList) {
        this.codeList = codeList;
    }

    public String getCodeName(String codeValue) {
        for (DictCode code : codeList) {
            if (codeValue.equals(code.getValue())) {
                return code.getName();
            }
        }

        return "";
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DictCategory");
        sb.append("{id='").append(id).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", isValid=").append(isValid);
        sb.append(", sortNum=").append(sortNum);
        sb.append(", inputDate=").append(inputDate);
        sb.append('}');
        return sb.toString();
    }
}
