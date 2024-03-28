package com.projects.study.servlet;

import com.projects.study.dao.Dao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.mapper.ExchangeRateMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import static com.projects.study.ControllerUtils.*;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    Dao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    ExchangerMapper<ExchangeRate> mapper = new ExchangeRateMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<ExchangeRate> rates = exchangeRateService.getAll();
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(rates));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> params = req.getParameterMap();
        validateParams(params);
        ExchangeRate savedExchangeRate = exchangeRateService.save(mapper.toEntity(params));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        try {
            PrintWriter writer = resp.getWriter();
            writer.println(convertToJson(savedExchangeRate));
            writer.close();
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

}
