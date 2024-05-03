package com.projects.study.service;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
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
                () -> new CurrencyNotFoundException(String.format(ExceptionMessage.FORMATTED_CUR_NOT_FOUND, code)));
    }

    public Currency save(Currency currency) {
        return exchangerDao.save(currency);
    }

    public boolean isExist(String code) {
        return exchangerDao.isExist(code);
    }

}
