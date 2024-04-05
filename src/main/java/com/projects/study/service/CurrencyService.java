package com.projects.study.service;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.CurrencyAlreadyExistException;
import com.projects.study.exception.CurrencyNotFoundException;

import java.util.List;

public class CurrencyService {
    ExchangerDao<Currency> exchangerDao;

    public CurrencyService(ExchangerDao<Currency> exchangerDao) {
        this.exchangerDao = exchangerDao;
    }

    public List<Currency> getAll() {
        return exchangerDao.getAll().toList();
    }

    public Currency get(String code) {
        return exchangerDao.get(code).orElseThrow(
                () -> new CurrencyNotFoundException(String.format("Currency with code %s not found", code)));
    }

    public Currency save(Currency currency) {
        if (exchangerDao.get(currency.getCode()).isPresent()) {
            throw new CurrencyAlreadyExistException(
                    String.format("Currency with code %s already exist", currency.getCode()));
        }
        return exchangerDao.save(currency);
    }

    public boolean isExist(String code) {
        return exchangerDao.isExist(code);
    }
}
