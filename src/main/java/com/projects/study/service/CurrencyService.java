package com.projects.study.service;

import com.projects.study.DAO.Dao;
import com.projects.study.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class CurrencyService {
    Dao<Currency> dao;

    public CurrencyService(Dao<Currency> dao) {
        this.dao = dao;
    }

    public List<Currency> getAll() throws SQLException {
        return dao.getAll();
    }

}
