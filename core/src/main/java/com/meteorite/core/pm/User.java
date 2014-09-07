package com.meteorite.core.pm;

/**
 * 用户信息
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class User {
    /** id */
    private String id;
    /** 名称 */
    private String name;
    /** 显示名 */
    private String displayName;
    /** 密码 */
    private String pwd;
    /** 邮箱 */
    private String email;
    /** 手机号 */
    private String mobileNumber;
    /** 是否有效 */
    private String isValid;
    /** 录入时间 */
    private String inputDate;

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
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }
    public String getInputDate() {
        return inputDate;
    }

    public void setInputDate(String inputDate) {
        this.inputDate = inputDate;
    }
}
