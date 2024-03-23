package com.projects.study.service;

import com.projects.study.DAO.Dao;
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
        return dao.getAll();
    }

    public ExchangeRate getByCode(String code) {
        return dao.getByCode(code).orElseThrow(
                () -> new ExchangeRateNotFoundException(String.format("Exchange rate with code %s not found", code)));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String code = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
        if (dao.getByCode(code).isPresent()) {
            throw new ExchangeRateAlreadyExistException(
                    String.format("Exchange rate with code %s already exist", code));
        }
        return dao.save(exchangeRate).get();
    }

    public ExchangeRate update(String code, String rate) {
        ExchangeRate exchangeRate = getByCode(code);
        exchangeRate.setRate(new BigDecimal(rate));
        dao.update(exchangeRate);
        return exchangeRate;
    }

}
