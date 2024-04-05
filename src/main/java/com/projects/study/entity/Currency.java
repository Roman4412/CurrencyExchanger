package com.projects.study.entity;

public class Currency {
    private long id;
    private String code;
    private String fullName;
    private String sign;

    public Currency() {
    }

    public String getCode() {
        return code;
    }

    public String getFullName() {
        return fullName;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSign() {
        return sign;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", code='" + code + '\'' + ", fullName='" + fullName + '\'' + ", sign='" + sign + '\'' + '}';
    }

}
