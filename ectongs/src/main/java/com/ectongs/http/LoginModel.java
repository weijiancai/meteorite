/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ectongs.http;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Date;

/** *
 * @author wei_jc
 */
public class LoginModel {
    private static LoginModel instance;
    
    private String errorMsgText;
    private boolean judgeDataRight;
    private Date loginTime;
    private String schemeId;
    private String serverTime;
    private String sessionId;
    private String stationAtt;
    private String stationId;
    private String userId;
    private String userName;
    private String dbms;
    private String baseLicenseInfo;
    private String returnCode;
    private String returnErrorMsg;
    private boolean isPrivateServer;
    private String privateServerUrl;
    
    private StringProperty loginName = new SimpleStringProperty();
    private StringProperty password = new SimpleStringProperty();
    private BooleanProperty savePassword = new SimpleBooleanProperty();
    private BooleanProperty autoLogin = new SimpleBooleanProperty();
    
    private LoginModel() {}
    
    public static LoginModel getInstance() {
        if(instance == null) {
            instance = new LoginModel();
        }
        return instance;
    }
    
    public static void destroy() {
        instance = null;
    }

    public String getErrorMsgText() {
        return errorMsgText;
    }

    public void setErrorMsgText(String errorMsgText) {
        this.errorMsgText = errorMsgText;
    }

    public boolean isJudgeDataRight() {
        return judgeDataRight;
    }

    public void setJudgeDataRight(boolean judgeDataRight) {
        this.judgeDataRight = judgeDataRight;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getSchemeId() {
        return schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public String getServerTime() {
        return serverTime;
    }

    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getStationAtt() {
        return stationAtt;
    }

    public void setStationAtt(String stationAtt) {
        this.stationAtt = stationAtt;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDbms() {
        return dbms;
    }

    public void setDbms(String dbms) {
        this.dbms = dbms;
    }

    public String getBaseLicenseInfo() {
        return baseLicenseInfo;
    }

    public void setBaseLicenseInfo(String baseLicenseInfo) {
        this.baseLicenseInfo = baseLicenseInfo;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnErrorMsg() {
        return returnErrorMsg;
    }

    public void setReturnErrorMsg(String returnErrorMsg) {
        this.returnErrorMsg = returnErrorMsg;
    }

    public boolean isClient() {
        return "C".equals(getStationAtt());
    }

    public boolean isPrivateServer() {
        return isPrivateServer;
    }

    public void setPrivateServer(boolean privateServer) {
        isPrivateServer = privateServer;
    }

    public String getPrivateServerUrl() {
        return privateServerUrl;
    }

    public void setPrivateServerUrl(String privateServerUrl) {
        this.privateServerUrl = privateServerUrl;
    }
    
    public String getLoginName() {
        return loginName.get();
    }

    public void setLoginName(String loginName) {
        this.loginName.set(loginName);
    }
    
    public StringProperty loginNameProperty() {
        return this.loginName;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }
    
    public StringProperty passwordProperty() {
        return this.password;
    }

    public boolean getSavePassword() {
        return savePassword.get();
    }

    public void setSavePassword(boolean savePassword) {
        this.savePassword.set(savePassword);
    }
    
    public BooleanProperty savePasswordProperty() {
        return this.savePassword;
    }

    public boolean getAutoLogin() {
        return autoLogin.get();
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin.set(autoLogin);
    }
    
    public BooleanProperty autoLoginProperty() {
        return this.autoLogin;
    }
}
