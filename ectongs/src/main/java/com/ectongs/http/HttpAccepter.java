/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.http;

import cc.csdn.base.util.UtilNumber;

import java.util.Map;

/**
 *
 * @author wei_jc
 */
public class HttpAccepter {
    private boolean isShowMsg;
    private String data;
    private String error;
    private String message;
    private String returnCode;
    private String returnContent;
    private Map<String, String> params;

    public void acceptComplete() {
        
    }
    
    //public void addExecCompleteListener(Function listener){}
    
    //public void fault(Object info){}

    public String getData() {
        return data;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    //public XMLList getDataXmlList(){}
    
    public String getError() {
        return error;
    }
    
    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public int getReturnCode() {
        return UtilNumber.toInt(returnCode);
    }
    
    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public boolean isShowMsg() {
        return isShowMsg;
    }

    public void setShowMsg(boolean isShowMsg) {
        this.isShowMsg = isShowMsg;
    }

    public String getReturnContent() {
        return returnContent;
    }

    public void setReturnContent(String returnContent) {
        this.returnContent = returnContent;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public boolean isSuccess() {
        if (getReturnCode() == -99) {
            return false;
        } else if (getReturnCode() < 0) {
            return false;
        } else if (getReturnCode() == 99 ) {
            return true;
        } else {
            return true;
        }
    }

    public boolean isNoMsgSuccess() {
        if (getReturnCode() == -99) {
            return false;
        } else if (getReturnCode() < 0) {
            return false;
        } else if (getReturnCode() == 99 ) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public String toString() {
        return "HttpAccepter{" +
                "isShowMsg=" + isShowMsg +
                ", data='" + data + '\'' +
                ", error='" + error + '\'' +
                ", message='" + message + '\'' +
                ", returnCode='" + returnCode + '\'' +
                ", returnContent='" + returnContent + '\'' +
                ", params=" + params +
                '}';
    }
}
