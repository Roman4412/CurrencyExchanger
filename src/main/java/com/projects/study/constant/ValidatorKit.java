package com.projects.study.constant;

public class ValidatorKit {
    public static final String CUR_CODE_PATTERN = "^[a-zA-Z]{3}$";
    public static final String RATE_CODE_PATTERN = "^[a-zA-Z]{6}$";
    public static final String RATE_NUM_PATTERN = "^\\d+([,.]\\d{1,2})?$";
    public static final int CUR_NAME_MAX_SIZE = 100;
    public static final int CUR_SIGN_MAX_SIZE = 2;
    public static final String RATE_MIN = "0.000001";
    public static final String AMOUNT_MIN = "0.01";

}
