package com.projects.study.service;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class ExchangeService {
    public static final String DEFAULT_CUR_CODE = "USD";
    public static final int RATE_SCALE = 4;
    public static final int AMOUNT_SCALE = 2;

    ExchangeRateService exRateService;
    CurrencyService currencyService;

    public ExchangeService(ExchangeRateService exRateService, CurrencyService currencyService) {
        this.exRateService = exRateService;
        this.currencyService = currencyService;
    }

    public BigDecimal exchange(String from, String to, BigDecimal amount) {
        BigDecimal convertedAmount;
        String directExCode = from + to;
        String reverseExCode = to + from;
        String defaultBaseCode = DEFAULT_CUR_CODE + from;
        String defaultTargetCode = DEFAULT_CUR_CODE + to;

        if (exRateService.isExist(directExCode)) {
            BigDecimal rate = exRateService.get(directExCode).getRate();
            convertedAmount = amount.multiply(rate);
        } else if (exRateService.isExist(reverseExCode)) {
            BigDecimal reverseRate = calculateReverseRate(reverseExCode);
            convertedAmount = amount.multiply(reverseRate);
            saveExchangeRate(from, to, reverseRate);
        } else if (exRateService.isExist(defaultBaseCode) && exRateService.isExist(defaultTargetCode)) {
            BigDecimal crossRate = calculateCrossRate(defaultBaseCode, defaultTargetCode);
            convertedAmount = amount.multiply(crossRate);
            saveExchangeRate(from, to, crossRate);
        } else {
            throw new ExchangeRateNotFoundException(ExceptionMessage.EX_CANT_EXCHANGE);
        }
        return convertedAmount.setScale(AMOUNT_SCALE, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateReverseRate(String code) {
        BigDecimal rate = exRateService.get(code).getRate();
        return BigDecimal.ONE.divide(rate, RATE_SCALE, RoundingMode.HALF_EVEN);

    }

    private BigDecimal calculateCrossRate(String defBaseCode, String defTargetCode) {
        BigDecimal defBaseRate = exRateService.get(defBaseCode).getRate();
        BigDecimal defTargetRate = exRateService.get(defTargetCode).getRate();
        return defBaseRate.divide(defTargetRate, RATE_SCALE, RoundingMode.HALF_EVEN);
    }

    private void saveExchangeRate(String base, String target, BigDecimal rate) {
        ExchangeRate newExchangeRate = new ExchangeRate();
        newExchangeRate.setBaseCurrency(currencyService.get(base));
        newExchangeRate.setTargetCurrency(currencyService.get(target));
        newExchangeRate.setRate(rate);
        exRateService.save(newExchangeRate);
    }

}