package com.meteorite.core.rest;

import com.meteorite.core.datasource.DataMap;

import java.util.List;

/**
 * Rest Response
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class Response {
    private boolean isSuccess;
    private String errorMsg;
    private List<DataMap> listData;

    public void setListData(List<DataMap> listData) {
        this.listData = listData;
    }

    public List<DataMap> getListData() {
        return listData;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
