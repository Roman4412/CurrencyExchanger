package com.projects.study.servlet;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.mapper.CurrencyMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static com.projects.study.util.ControllerUtils.*;
import static com.projects.study.util.ValidatorUtils.*;

@WebServlet("/currencies")
public class CurrenciesServlet extends HttpServlet {
    private final ExchangerDao<Currency> currencyExchangerDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyExchangerDao);
    ExchangerMapper<Currency> mapper = new CurrencyMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<Currency> currencies = currencyService.getAll();
        sendResponse(convertToJson(currencies), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, String[]> params = req.getParameterMap();
        validateForNull(params);
        Currency currency = currencyService.save(mapper.toEntity(params));
        resp.setStatus(HttpServletResponse.SC_CREATED);
        sendResponse(convertToJson(currency), resp);
    }

}
