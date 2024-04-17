package com.projects.study.service;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateAlreadyExistException;
import com.projects.study.exception.ExchangeRateNotFoundException;
import com.projects.study.exception.IllegalParameterException;

import java.math.BigDecimal;
import java.util.List;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ValidatorUtils.*;

public class ExchangeRateService {
    ExchangerDao<ExchangeRate> exchangerDao;

    public ExchangeRateService(ExchangerDao<ExchangeRate> exchangerDao) {
        this.exchangerDao = exchangerDao;
    }

    public List<ExchangeRate> getAll() {
        return exchangerDao.getAll().toList();
    }

    public ExchangeRate get(String code) {
        if (isValidString(RATE_CODE_PATTERN, code)) {
            return exchangerDao.get(code).orElseThrow(() -> new ExchangeRateNotFoundException(
                    String.format("Exchange rate with code %s not found", code)));
        } else {
            throw new IllegalParameterException("exchange rate code must consist of 6 latin letters");
        }

    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        if(isValidRate(exchangeRate.getRate())) {
            String code = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
            if (exchangerDao.get(code).isPresent()) {
                throw new ExchangeRateAlreadyExistException(
                        String.format("Exchange rate with code %s already exist", code));
            }
            return exchangerDao.save(exchangeRate);
        } else {
            throw new IllegalParameterException("the rate must be greater than zero");
        }
    }

    public ExchangeRate update(String code, BigDecimal rate) {
        if (isValidString(RATE_CODE_PATTERN, code) && isValidRate(rate)) {
            ExchangeRate exchangeRate = get(code);
            exchangeRate.setRate(rate);
            exchangerDao.update(exchangeRate);
            return exchangeRate;
        } else {
            throw new IllegalParameterException("code or rate is not valid");
        }

    }

    public boolean isExist(String code) {
        return exchangerDao.isExist(code);
    }

}
