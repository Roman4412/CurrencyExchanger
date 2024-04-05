package com.projects.study.service;

import com.projects.study.dao.Dao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateAlreadyExistException;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.math.BigDecimal;
import java.util.List;

public class ExchangeRateService {
    Dao<ExchangeRate> dao;

    public ExchangeRateService(Dao<ExchangeRate> dao) {
        this.dao = dao;
    }

    public List<ExchangeRate> getAll() {
        return dao.getAll().toList();
    }

    public ExchangeRate get(String code) {
        return dao.get(code).orElseThrow(
                () -> new ExchangeRateNotFoundException(String.format("Exchange rate with code %s not found", code)));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String code = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
        if (dao.get(code).isPresent()) {
            throw new ExchangeRateAlreadyExistException(
                    String.format("Exchange rate with code %s already exist", code));
        }
        return dao.save(exchangeRate);
    }

    public ExchangeRate update(String code, String rate) {
        ExchangeRate exchangeRate = get(code);
        exchangeRate.setRate(new BigDecimal(rate));
        dao.update(exchangeRate);
        return exchangeRate;
    }

    public boolean isExist(String code) {
        return dao.isExist(code);
    }

}
