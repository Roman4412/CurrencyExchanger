package com.projects.study.constant;

import java.math.BigDecimal;

public final class ValidatorKit {
    public static final String CUR_CODE_REGEX = "^[a-zA-Z]{3}$";
    public static final String CUR_NAME_REGEX = "^^[a-zA-Z\\s]{3,100}$";
    public static final String CUR_SIGN_REGEX = "^[a-zA-Z]{1,2}$";

    public static final String ER_CODE_REGEX = "^[a-zA-Z]{6}$";
    public static final String ER_RATE_REGEX = "^\\d+(?:[\\.,]\\d{0,4})?$";
    public static final BigDecimal ER_MIN_RATE = new BigDecimal("0.0001");

    public static final String EX_AMOUNT_REGEX = "^\\d+(?:[\\.,]\\d{0,2})?$";
    public static final BigDecimal EX_MIN_AMOUNT = new BigDecimal("0.01");

    private ValidatorKit() {
    }

}

