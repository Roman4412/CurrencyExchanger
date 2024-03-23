package com.projects.study.service;

import com.projects.study.DAO.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.CurrencyAlreadyExistException;
import com.projects.study.exception.CurrencyNotFoundException;

import java.util.List;

public class CurrencyService {
    Dao<Currency> dao;

    public CurrencyService(Dao<Currency> dao) {
        this.dao = dao;
    }

    public List<Currency> getAll() {
        return dao.getAll().toList();
    }

    public Currency getByCode(String code) {
        return dao.getByCode(code).orElseThrow(
                () -> new CurrencyNotFoundException(String.format("Currency with code %s not found", code)));
    }

    public Currency save(Currency currency) {
        if (dao.getByCode(currency.getCode()).isPresent()) {
            throw new CurrencyAlreadyExistException(
                    String.format("Currency with code %s already exist", currency.getCode()));
        }
        return dao.save(currency);
    }

}
