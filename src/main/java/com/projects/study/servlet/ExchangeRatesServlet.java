package com.projects.study.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projects.study.DAO.CurrencyDao;
import com.projects.study.DAO.Dao;
import com.projects.study.DAO.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.CurrencyNotFoundException;
import com.projects.study.exception.ExchangeRateAlreadyExistException;
import com.projects.study.exception.ExchangerExceptionHandler;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        try (PrintWriter writer = resp.getWriter()) {
            List<ExchangeRate> rates = exchangeRateService.getAll();
            writer.println(jsonMapper.writeValueAsString(rates));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF8");

        String baseCurCode = req.getParameter("baseCurCode");
        String targetCurCode = req.getParameter("targetCurCode");
        String rate = req.getParameter("rate");
        if (baseCurCode.isBlank() || targetCurCode.isBlank() || rate.isBlank()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } else {
            try {
                ExchangeRate newExchangeRate = new ExchangeRate();
                Currency baseCur = currencyService.getCurrencyByCode(baseCurCode);
                Currency targetCur = currencyService.getCurrencyByCode(targetCurCode);
                newExchangeRate.setBaseCurrency(baseCur);
                newExchangeRate.setTargetCurrency(targetCur);
                newExchangeRate.setRate(new BigDecimal(rate));
                ExchangeRate savedExchangeRate = exchangeRateService.save(newExchangeRate);

                resp.setStatus(HttpServletResponse.SC_CREATED);
                PrintWriter writer = resp.getWriter();
                String rateAsJson = jsonMapper.writeValueAsString(savedExchangeRate);
                writer.println(rateAsJson);
                writer.close();
            } catch (ExchangeRateAlreadyExistException | CurrencyNotFoundException e) {
                ExchangerExceptionHandler.handle(req, resp, e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
