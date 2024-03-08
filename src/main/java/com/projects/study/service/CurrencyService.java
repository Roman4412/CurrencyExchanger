package com.projects.study.service;

import com.projects.study.DAO.DAO;
import com.projects.study.entity.Currency;

import java.sql.SQLException;
import java.util.List;

public class CurrencyService {
    DAO<Currency> dao;

    public CurrencyService(DAO<Currency> dao) {
        this.dao = dao;
    }

    public List<Currency> getAll() throws SQLException {
        return dao.getAll();
    }

}
