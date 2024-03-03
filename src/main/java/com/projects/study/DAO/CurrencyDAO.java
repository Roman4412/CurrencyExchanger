package com.projects.study.DAO;

import com.projects.study.entity.Currency;

import java.util.List;
import java.util.Optional;

public class CurrencyDAO implements DAO<Currency> {
    private static CurrencyDAO currencyDAO;

    private CurrencyDAO() {
    }

    public static CurrencyDAO getInstance() {
        if (currencyDAO == null) {
            currencyDAO = new CurrencyDAO();
        }
        return currencyDAO;
    }


    @Override
    public Optional<Currency> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<Currency> getAll() {
        return null;
    }

    @Override
    public void save(Currency currency) {

    }

    @Override
    public void delete(long id) {

    }
}
