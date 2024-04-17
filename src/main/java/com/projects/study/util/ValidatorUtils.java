package com.projects.study.util;

import com.projects.study.constant.ValidatorKit;
import com.projects.study.entity.Currency;
import com.projects.study.exception.IllegalParameterException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Map;
import java.util.regex.Pattern;

import static com.projects.study.constant.ValidatorKit.*;


public final class ValidatorUtils {
    private ValidatorUtils() {
    }

    public static boolean isValidString(String pattern, String param) {
        return Pattern.matches(pattern, param);
    }

    public static boolean isValidCurrency(Currency currency) {
        String code = currency.getCode();
        String name = currency.getFullName();
        String sign = currency.getSign();

        return name.length() <= ValidatorKit.CUR_NAME_MAX_SIZE
               && isValidString(ValidatorKit.CUR_CODE_PATTERN, code)
               && sign.length() <= ValidatorKit.CUR_SIGN_MAX_SIZE;
    }

    public static boolean isValidRate(BigDecimal rate) {
        return rate.compareTo(new BigDecimal(RATE_MIN, MathContext.DECIMAL32)) >= 0;
    }

    public static boolean isValidAmount(BigDecimal amount) {
        return amount.compareTo(new BigDecimal(AMOUNT_MIN, MathContext.DECIMAL32)) >= 0
               && amount.scale() <= 2;
    }

    public static void validateForNull(Map<String, String[]> params) {
        for(String p : params.keySet()) {
            if ((params.get(p)[0].isBlank() || params.get(p)[0] == null)) {
                throw new IllegalParameterException(String.format("Missing parameter %s", p));
            }
        }
    }

}