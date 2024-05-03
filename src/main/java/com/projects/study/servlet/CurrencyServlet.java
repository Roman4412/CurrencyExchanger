package com.projects.study.servlet;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.entity.Currency;
import com.projects.study.exception.InvalidParameterException;
import com.projects.study.service.CurrencyService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ServletUtils.*;


@WebServlet("/currency/*")
public class CurrencyServlet extends HttpServlet {
    private final ExchangerDao<Currency> currencyExchangerDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyExchangerDao);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = parsePathVar(req);
        if (!isValidString(CUR_CODE_REGEX, code)) {
            throw new InvalidParameterException(ExceptionMessage.INVALID_CURRENCY_CODE);
        } else {
            Currency currency = currencyService.get(code.trim());
            sendResponse(convertToJson(currency), resp);
        }
    }

}
