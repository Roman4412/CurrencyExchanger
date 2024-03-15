package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDao;
import com.projects.study.DAO.Dao;
import com.projects.study.DAO.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    Dao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    Dao<Currency> currencyDao = CurrencyDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    private final ObjectMapper jsonMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        PrintWriter writer = resp.getWriter();
        List<ExchangeRate> rates = exchangeRateService.getAll();
        writer.println(jsonMapper.writeValueAsString(rates));
        writer.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String baseCurCode = req.getParameter("baseCurCode");
        String targetCurCode = req.getParameter("targetCurCode");
        String rate = req.getParameter("rate");
        if (baseCurCode.isBlank() || targetCurCode.isBlank() || rate.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {

            ExchangeRate newExchangeRate = new ExchangeRate();
            Currency baseCur = currencyDao.getByCode(baseCurCode).orElseThrow();
            Currency targetCur = currencyDao.getByCode(targetCurCode).orElseThrow();
            newExchangeRate.setBaseCurrency(baseCur);
            newExchangeRate.setTargetCurrency(targetCur);
            newExchangeRate.setRate(new BigDecimal(rate));
            Optional<ExchangeRate> savedExchangeRate = exchangeRateService.save(newExchangeRate);

            if (savedExchangeRate.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter writer = resp.getWriter();
                newExchangeRate = savedExchangeRate.get();

                writer.println(jsonMapper.writeValueAsString(newExchangeRate));
            } else {
                resp.setStatus(HttpServletResponse.SC_CONFLICT);
            }
        }

    }

}
