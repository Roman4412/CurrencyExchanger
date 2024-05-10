package com.projects.study.constant;

public final class ExceptionMessage {
    public static final String CUR_INVALID_CODE = "the currency code must consist of 3 latin letters";
    public static final String CUR_INVALID_NAME = "the currency name must contain from 1 to 100 latin characters";
    public static final String CUR_INVALID_SIGN = "the currency sign must consist of 1 or 2 latin characters";
    public static final String CUR_MISS_CODE = "Missing code in the query string";
    public static final String CUR_EXIST_FORMATTED = "Currency with code %s already exist";
    public static final String CUR_NOT_FOUND_FORMATTED = "Currency with code %s not found";

    public static final String ER_INVALID_CODE = "the exchange rate code must consist of 6 latin letters";
    public static final String ER_INVALID_RATE_FORMATTED = "the rate must be no less than %s and have no more than 4 decimal places";
    public static final String ER_NOT_FOUND_FORMATTED = "Exchange rate with code %s not found";
    public static final String ER_EXIST_FORMATTED = "Exchange rate with code %s already exist";

    public static final String EX_CANT_EXCHANGE = "course not found and can't be calculated";
    public static final String EX_INVALID_AMOUNT_FORMATTED = "the amount must be no less than %s and have no more than 2 decimal places";

    private ExceptionMessage() {
    }

}
