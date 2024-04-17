package com.projects.study.service;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.CurrencyAlreadyExistException;
import com.projects.study.exception.CurrencyNotFoundException;
import com.projects.study.exception.IllegalParameterException;

import java.util.List;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ValidatorUtils.*;

public class CurrencyService {
    ExchangerDao<Currency> exchangerDao;

    public CurrencyService(ExchangerDao<Currency> exchangerDao) {
        this.exchangerDao = exchangerDao;
    }

    public List<Currency> getAll() {
        return exchangerDao.getAll().toList();
    }

    public Currency get(String code) {
        if (isValidString(CUR_CODE_PATTERN, code)) {
            return exchangerDao.get(code).orElseThrow(
                    () -> new CurrencyNotFoundException(String.format("Currency with code %s not found", code)));
        } else {
            throw new IllegalParameterException("currency code must consist of 3 latin letters");
        }
    }

    public Currency save(Currency currency) {
        if (isValidCurrency(currency)) {
            if (exchangerDao.get(currency.getCode()).isPresent()) {
                throw new CurrencyAlreadyExistException(
                        String.format("Currency with code %s already exist", currency.getCode()));
            }
            return exchangerDao.save(currency);
        } else {
            throw new IllegalParameterException("Currency is not valid");
        }
    }

    public boolean isExist(String code) {
        if (isValidString(CUR_CODE_PATTERN, code)) {
            return exchangerDao.isExist(code);
        } else {
            throw new IllegalParameterException("currency code must consist of 3 latin letters");
        }
    }

}
