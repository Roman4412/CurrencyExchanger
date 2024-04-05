package com.projects.study.service;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.Dao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ConvertibleAmountException;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.math.MathContext;

public class ExchangeService {
    public static final String DEF_CUR_CODE = "USD";
    Dao<ExchangeRate> exRateDao = ExchangeRateDao.getInstance();
    ExchangeRateService exRateService = new ExchangeRateService(exRateDao);
    Dao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);

    public BigDecimal exchange(String base, String target, BigDecimal amount) {
        if (amount.doubleValue() < 1) {
            throw new ConvertibleAmountException("the amount mast be at least 1");
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
        return convertedAmount;
    }

}