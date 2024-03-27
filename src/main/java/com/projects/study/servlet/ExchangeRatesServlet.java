package com.projects.study.servlet;

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

import static com.projects.study.ControllerUtils.*;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    Dao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    Dao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);

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
        validateParams(req.getParameterMap());
        String baseCurCode = formatParam(req.getParameter("baseCurCode"));
        String targetCurCode = formatParam(req.getParameter("targetCurCode"));
        String rate = req.getParameter("rate");

        ExchangeRate newExchangeRate = new ExchangeRate();
        Currency baseCur = currencyService.getByCode(baseCurCode);
        Currency targetCur = currencyService.getByCode(targetCurCode);
        newExchangeRate.setBaseCurrency(baseCur);
        newExchangeRate.setTargetCurrency(targetCur);
        newExchangeRate.setRate(new BigDecimal(rate));

        ExchangeRate savedExchangeRate = exchangeRateService.save(newExchangeRate);
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
