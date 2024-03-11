package com.projects.study.DAO;

import com.projects.study.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateDao implements Dao<ExchangeRate> {

    private static ExchangeRateDao exchangeRateDAO;

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        if (exchangeRateDAO == null) {
            exchangeRateDAO = new ExchangeRateDao();
        }
        return exchangeRateDAO;
    }

    @Override
    public Optional<ExchangeRate> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> getAll() {
        return null;
    }

    @Override
    public Optional<ExchangeRate> save(ExchangeRate exchangeRate) {
        return Optional.ofNullable(new ExchangeRate());
    }

    @Override
    public Optional<ExchangeRate> delete(long id) {
        return Optional.ofNullable(new ExchangeRate());
    }

}
