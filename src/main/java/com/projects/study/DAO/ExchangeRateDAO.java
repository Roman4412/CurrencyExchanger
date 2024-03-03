package com.projects.study.DAO;

import com.projects.study.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateDAO implements DAO<ExchangeRate> {

    private static ExchangeRateDAO exchangeRateDAO;

    private ExchangeRateDAO() {
    }

    public static ExchangeRateDAO getInstance() {
        if (exchangeRateDAO == null) {
            exchangeRateDAO = new ExchangeRateDAO();
        }
        return exchangeRateDAO;
    }

    @Override
    public Optional<ExchangeRate> get(long id) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> getAll() {
        return null;
    }

    @Override
    public void save(ExchangeRate exchangeRate) {

    }

    @Override
    public void delete(long id) {

    }
}
