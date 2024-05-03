package com.projects.study.entity;

public class Currency {
    private long id;
    private String code;
    private String name;
    private String sign;

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    @Override
    public String toString() {
        return "Currency{" + "id=" + id + ", code='" + code + '\'' + ", name='" + name + '\'' + ", sign='" + sign + '\'' + '}';
    }

}
