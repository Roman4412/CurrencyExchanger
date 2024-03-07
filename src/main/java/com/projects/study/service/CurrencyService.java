package com.projects.study.service;

import com.projects.study.DAO.DAO;
import com.projects.study.entity.Currency;

import java.util.List;

public class CurrencyService {
    DAO<Currency> dao;

    public CurrencyService(DAO<Currency> dao) {
        this.dao = dao;
    }

    public List<Currency> getAll() {
        return dao.getAll();
    }


}
