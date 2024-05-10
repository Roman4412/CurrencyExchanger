package com.projects.study.servlet;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.constant.RequestParams;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.InvalidParameterException;
import com.projects.study.mapper.CurrencyMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ServletUtils.*;


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
        String code = req.getParameter(RequestParams.CUR_CODE);
        String name = req.getParameter(RequestParams.CUR_NAME);
        String sign = req.getParameter(RequestParams.CUR_SIGN);
        if (!isValidStringParam(CUR_CODE_REGEX, code)) {
            throw new InvalidParameterException(ExceptionMessage.CUR_INVALID_CODE);
        } else if (!isValidStringParam(CUR_NAME_REGEX, name)) {
            throw new InvalidParameterException(ExceptionMessage.CUR_INVALID_NAME);
        } else if (!isValidStringParam(CUR_SIGN_REGEX, sign)) {
            throw new InvalidParameterException(ExceptionMessage.CUR_INVALID_SIGN);
        } else {
            Currency currency = currencyService.save(mapper.toEntity(req.getParameterMap()));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendResponse(convertToJson(currency), resp);
        }
    }

}
