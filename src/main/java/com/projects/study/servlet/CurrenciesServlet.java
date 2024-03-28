package com.projects.study.servlet;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.mapper.CurrencyMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.projects.study.ControllerUtils.*;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangerMapper<Currency> mapper = new CurrencyMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<Currency> currencies = currencyService.getAll();
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(currencies));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> params = req.getParameterMap();
        validateParams(params);
        Currency currency = currencyService.save(mapper.toEntity(params));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(currency));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
