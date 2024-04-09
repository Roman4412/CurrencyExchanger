package com.projects.study.service;

import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateNotFoundException;
import com.projects.study.exception.IllegalParameterException;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.projects.study.util.ValidatorUtils.*;

public class ExchangeService {
    public static final String DEF_CUR_CODE = "USD";
    ExchangeRateService exRateService;
    CurrencyService currencyService;

    public ExchangeService(ExchangeRateService exRateService, CurrencyService currencyService) {
        this.exRateService = exRateService;
        this.currencyService = currencyService;
    }

    public BigDecimal exchange(String base, String target, BigDecimal amount) {
        if (!isValidAmount(amount)) {
            throw new IllegalParameterException("the amount must be greater than zero and have no more than two decimal places");
        }

        BigDecimal convertedAmount;
        if (exRateService.isExist(base + target)) {
            BigDecimal rate = exRateService.get(base + target).getRate();
            convertedAmount = amount.multiply(rate);
        } else if (exRateService.isExist(target + base)) {
            BigDecimal rate = exRateService.get(target + base).getRate();
            BigDecimal newRate = BigDecimal.ONE.divide(rate, MathContext.DECIMAL32);
            convertedAmount = amount.multiply(newRate);

            ExchangeRate newExchangeRate = new ExchangeRate();
            newExchangeRate.setBaseCurrency(currencyService.get(base));
            newExchangeRate.setTargetCurrency(currencyService.get(target));
            newExchangeRate.setRate(newRate);
            exRateService.save(newExchangeRate);
        } else if (exRateService.isExist(DEF_CUR_CODE + base) && exRateService.isExist(DEF_CUR_CODE + target)) {
            BigDecimal defBaseRate = exRateService.get(DEF_CUR_CODE + base).getRate();
            BigDecimal defTargetRate = exRateService.get(DEF_CUR_CODE + target).getRate();
            BigDecimal baseTargetRate = defBaseRate.divide(defTargetRate, MathContext.DECIMAL32);
            convertedAmount = amount.multiply(baseTargetRate);

            ExchangeRate newExchangeRate = new ExchangeRate();
            newExchangeRate.setBaseCurrency(currencyService.get(base));
            newExchangeRate.setTargetCurrency(currencyService.get(target));
            newExchangeRate.setRate(baseTargetRate);
            exRateService.save(newExchangeRate);
        } else {
            throw new ExchangeRateNotFoundException("course not found and cannot be created");
        }
        return convertedAmount.setScale(2, RoundingMode.HALF_EVEN);
    }

}