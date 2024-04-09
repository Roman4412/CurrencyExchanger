package com.projects.study.servlet;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.mapper.ExchangeRateMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.projects.study.util.ControllerUtils.*;
import static com.projects.study.util.ValidatorUtils.*;

@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    ExchangerDao<ExchangeRate> exchangeRateExchangerDao = ExchangeRateDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateExchangerDao);
    ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangerMapper<ExchangeRate> mapper = new ExchangeRateMapper(currencyService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<ExchangeRate> rates = exchangeRateService.getAll();
        sendResponse(convertToJson(rates), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> params = req.getParameterMap();
        validateForNull(params);
        ExchangeRate savedExchangeRate = exchangeRateService.save(mapper.toEntity(params));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        sendResponse(convertToJson(savedExchangeRate), resp);
    }

}
