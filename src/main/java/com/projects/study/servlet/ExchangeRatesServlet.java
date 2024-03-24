package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.Dao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.CurrencyService;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    Dao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    Dao<Currency> currencyDao = CurrencyDao.getInstance();

    CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        List<ExchangeRate> rates = exchangeRateService.getAll();
        writer.println(jsonMapper.writeValueAsString(rates));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String baseCurCode = req.getParameter("baseCurCode");
        String targetCurCode = req.getParameter("targetCurCode");
        String rate = req.getParameter("rate");
        if (baseCurCode.isBlank() || targetCurCode.isBlank() || rate.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            ExchangeRate newExchangeRate = new ExchangeRate();
            Currency baseCur = currencyService.getByCode(baseCurCode);
            Currency targetCur = currencyService.getByCode(targetCurCode);
            newExchangeRate.setBaseCurrency(baseCur);
            newExchangeRate.setTargetCurrency(targetCur);
            newExchangeRate.setRate(new BigDecimal(rate));
            ExchangeRate savedExchangeRate = exchangeRateService.save(newExchangeRate);

            resp.setStatus(HttpServletResponse.SC_CREATED);
            PrintWriter writer = resp.getWriter();
            String rateAsJson = jsonMapper.writeValueAsString(savedExchangeRate);
            writer.println(rateAsJson);
            writer.close();
        }
    }

}
