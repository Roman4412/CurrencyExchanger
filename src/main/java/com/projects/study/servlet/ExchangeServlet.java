package com.projects.study.servlet;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.dto.ExchangeResponse;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.CurrencyService;
import com.projects.study.service.ExchangeRateService;
import com.projects.study.service.ExchangeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import static com.projects.study.util.ControllerUtils.*;
import static com.projects.study.util.ValidatorUtils.*;

@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    ExchangerDao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangeService exchangeService = new ExchangeService(exchangeRateService, currencyService);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        validateForNull(req.getParameterMap());
        String base = req.getParameter("base").trim();
        String target = req.getParameter("target").trim();
        BigDecimal amount = new BigDecimal(req.getParameter("amount").trim().replace(',', '.'));
        //builder for response
        BigDecimal converted = exchangeService.exchange(base, target, amount);
        ExchangeResponse response = new ExchangeResponse();
        response.setExchangeRate(exchangeRateService.get(base + target));
        response.setAmount(amount);
        response.setConvertedAmount(converted);
        sendResponse(convertToJson(response), resp);
    }

}
