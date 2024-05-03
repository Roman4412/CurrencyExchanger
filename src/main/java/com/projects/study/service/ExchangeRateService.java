package com.projects.study.service;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.ExchangeRate;
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
                () -> new ExchangeRateNotFoundException(String.format(ExceptionMessage.FORMATTED_ER_NOT_FOUND, code)));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        return exchangerDao.save(exchangeRate);
    }

    public ExchangeRate update(String code, BigDecimal rate) {
        ExchangeRate exchangeRate = get(code);
        exchangeRate.setRate(rate);
        exchangerDao.update(exchangeRate);
        return exchangeRate;
    }

    public boolean isExist(String code) {
        return exchangerDao.isExist(code);
    }

}
