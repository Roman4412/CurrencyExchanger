package com.projects.study.service;

import com.projects.study.DAO.Dao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.ExchangeRateAlreadyExistException;
import com.projects.study.exception.ExchangeRateNotFoundException;

import java.util.List;

public class ExchangeRateService {
    Dao<ExchangeRate> dao;

    public ExchangeRateService(Dao<ExchangeRate> dao) {
        this.dao = dao;
    }

    public List<ExchangeRate> getAll() {
        return dao.getAll();
    }

    public ExchangeRate getExchangeRateByCode(String curPair) {
        return dao.getByCode(curPair).orElseThrow(() -> new ExchangeRateNotFoundException(
                String.format("Exchange rate with code %s not found", curPair)));
    }

    public ExchangeRate save(ExchangeRate exchangeRate) {
        String code = exchangeRate.getBaseCurrency().getCode() + exchangeRate.getTargetCurrency().getCode();
        if (dao.getByCode(code).isPresent()) {
            throw new ExchangeRateAlreadyExistException(
                    String.format("Exchange rate with code %s already exist", code));
        }
        return dao.save(exchangeRate).get();
    }

}
