package com.meteorite.core.datasource.request;

import com.meteorite.core.datasource.DataMap;

import java.util.List;

/**
 * Rest Response
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class BaseResponse implements IResponse {
    private boolean isSuccess;
    private String errorMsg;
    private List<DataMap> listData;

    public void setListData(List<DataMap> listData) {
        this.listData = listData;
    }

    @Override
    public List<DataMap> getListData() {
        return listData;
    }

    @Override
    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
