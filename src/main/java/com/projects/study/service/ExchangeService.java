package com.projects.study.service;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.dto.ExchangeResponse;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.math.RoundingMode;


public class ExchangeService {
    public static final String DEFAULT_CUR_CODE = "USD";
    public static final int RATE_SCALE = 4;
    public static final int AMOUNT_SCALE = 2;

    ExchangeRateService exchangeRateService;
    CurrencyService currencyService;

    public ExchangeService(ExchangeRateService exchangeRateService, CurrencyService currencyService) {
        this.exchangeRateService = exchangeRateService;
        this.currencyService = currencyService;
    }


    public ExchangeResponse exchange(String from, String to, BigDecimal amount) {
        ExchangeResponse response = new ExchangeResponse();
        BigDecimal convertedAmount;
        ExchangeRate exchangeRate;

        BigDecimal rate;
        String directRateCode = from + to;
        String reverseRateCode = to + from;
        String usdBaseRateCode = DEFAULT_CUR_CODE + from;
        String usdTargetRateCode = DEFAULT_CUR_CODE + to;

        if (exchangeRateService.isExist(directRateCode)) {
            exchangeRate = exchangeRateService.get(directRateCode);
            convertedAmount = amount.multiply(exchangeRate.getRate());
        } else if (exchangeRateService.isExist(reverseRateCode)) {
            rate = calculateReverseRate(reverseRateCode);
            convertedAmount = amount.multiply(rate);
            exchangeRate = saveExchangeRate(from, to, rate);
        } else if (exchangeRateService.isExist(usdBaseRateCode) && exchangeRateService.isExist(usdTargetRateCode)) {
            rate = calculateCrossRate(usdBaseRateCode, usdTargetRateCode);
            convertedAmount = amount.multiply(rate);
            exchangeRate = saveExchangeRate(from, to, rate);
        } else {
            throw new ExchangeRateNotFoundException(ExceptionMessage.EX_CANT_EXCHANGE);
        }

        response.setExchangeRate(exchangeRate);
        response.setAmount(amount);
        response.setConvertedAmount(convertedAmount.setScale(AMOUNT_SCALE, RoundingMode.HALF_EVEN));

        return response;
    }

    private BigDecimal calculateReverseRate(String code) {
        BigDecimal rate = exchangeRateService.get(code).getRate();
        return BigDecimal.ONE.divide(rate, RATE_SCALE, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calculateCrossRate(String defBaseCode, String defTargetCode) {
        BigDecimal defBaseRate = exchangeRateService.get(defBaseCode).getRate();
        BigDecimal defTargetRate = exchangeRateService.get(defTargetCode).getRate();
        return defBaseRate.divide(defTargetRate, RATE_SCALE, RoundingMode.HALF_EVEN);
    }

    private ExchangeRate saveExchangeRate(String base, String target, BigDecimal rate) {
        ExchangeRate newExchangeRate = new ExchangeRate();
        newExchangeRate.setBaseCurrency(currencyService.get(base));
        newExchangeRate.setTargetCurrency(currencyService.get(target));
        newExchangeRate.setRate(rate);
        return exchangeRateService.save(newExchangeRate);
    }

}