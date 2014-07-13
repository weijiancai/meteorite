package com.meteorite.core.parser.mobile;

/**
 * 手机号码
 *
 * @author wei_jc
 * @since 1.0.0
 */
public class MobileNumber {
    private String code; // 手机号码
    private String province; // 所在省
    private String city; // 所在市
    private String cardType; // 手机卡类型
    private String operators; // 运营商
    private String codeSegment; // 号段

    public MobileNumber(String code, String province, String city, String cardType, String operators, String codeSegment) {
        this.code = code;
        this.province = province;
        this.city = city;
        this.cardType = cardType;
        this.operators = operators;
        this.codeSegment = codeSegment;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOperators() {
        return operators;
    }

    public void setOperators(String operators) {
        this.operators = operators;
    }

    public String getCodeSegment() {
        return codeSegment;
    }

    public void setCodeSegment(String codeSegment) {
        this.codeSegment = codeSegment;
    }

    @Override
    public String toString() {
        return "MobileNumber{" +
                "code='" + code + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", cardType='" + cardType + '\'' +
                ", operators='" + operators + '\'' +
                ", codeSegment='" + codeSegment + '\'' +
                '}';
    }
}
