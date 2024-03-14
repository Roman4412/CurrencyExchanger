package com.projects.study.service;

import com.projects.study.DAO.Dao;
import com.projects.study.entity.ExchangeRate;

import java.util.List;
import java.util.Optional;

public class ExchangeRateService {
    Dao<ExchangeRate> dao;

    public ExchangeRateService(Dao<ExchangeRate> dao) {
        this.dao = dao;
    }

    public List<ExchangeRate> getAll() {
        return dao.getAll();
    }

    public Optional<ExchangeRate> getExchangeRateByCode(String curPair) {
        return dao.getByCode(curPair);
    }

}
