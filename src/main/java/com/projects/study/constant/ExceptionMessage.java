package com.projects.study.constant;

public final class ExceptionMessage {
    public static final String INVALID_CURRENCY_CODE = "the currency code must consist of 3 latin letters";
    public static final String INVALID_CURRENCY_NAME = "the currency name must contain from 1 to 100 latin characters";
    public static final String INVALID_CURRENCY_SIGN = "the currency sign must consist of 1 or 2 latin characters";
    public static final String MISS_CUR_CODE = "Missing code in the query string";
    public static final String FORMATTED_CUR_EXIST = "Currency with code %s already exist";
    public static final String FORMATTED_CUR_NOT_FOUND = "Currency with code %s not found";

    public static final String INVALID_ER_CODE = "the exchange rate code must consist of 6 latin letters";
    public static final String FORMATTED_INVALID_RATE = "the rate must be no less than %s and have no more than 4 decimal places";
    public static final String FORMATTED_ER_NOT_FOUND = "Exchange rate with code %s not found";
    public static final String FORMATTED_ER_EXIST = "Exchange rate with code %s already exist";

    public static final String EX_CANT_EXCHANGE = "course not found and can't be calculated";
    public static final String FORMATTED_INVALID_AMOUNT = "the amount must be no less than %s and have no more than 2 decimal places";

    private ExceptionMessage() {
    }

}
