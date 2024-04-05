package com.projects.study.service;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateAlreadyExistException;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {
    ExchangerDao<ExchangeRate> exchangerDao;

    public ExchangeRateService(ExchangerDao<ExchangeRate> exchangerDao) {
        this.exchangerDao = exchangerDao;
    }

    public List<ExchangeRate> getAll() {
        return exchangerDao.getAll().toList();
    }

    public ExchangeRate get(String code) {
        return exchangerDao.get(code).orElseThrow(
                () -> new ExchangeRateNotFoundException(String.format("Exchange rate with code %s not found", code)));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String code = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
        if (exchangerDao.get(code).isPresent()) {
            throw new ExchangeRateAlreadyExistException(
                    String.format("Exchange rate with code %s already exist", code));
        }
        return exchangerDao.save(exchangeRate);
    }

    public ExchangeRate update(String code, String rate) {
        ExchangeRate exchangeRate = get(code);
        exchangeRate.setRate(new BigDecimal(rate));
        exchangerDao.update(exchangeRate);
        return exchangeRate;
    }

    public boolean isExist(String code) {
        return exchangerDao.isExist(code);
    }

}
