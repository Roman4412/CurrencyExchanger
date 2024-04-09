package com.projects.study.servlet;

import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;
import java.math.MathContext;

import static com.projects.study.util.ControllerUtils.*;
import static com.projects.study.util.ValidatorUtils.*;

@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangerDao<ExchangeRate> exchangerDao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(exchangerDao);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        validateForNull(req.getParameterMap());
        String code = parsePathVar(req).toUpperCase().trim();
        BigDecimal rate = new BigDecimal(req.getParameter("rate")
                .replace(',', '.')
                .trim(), MathContext.DECIMAL32);
        ExchangeRate updatedRate = exchangeRateService.update(code, rate);
        sendResponse(convertToJson(updatedRate), resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = parsePathVar(req).toUpperCase().trim();
        ExchangeRate rate = exchangeRateService.get(code);
        sendResponse(convertToJson(rate), resp);
    }

}
