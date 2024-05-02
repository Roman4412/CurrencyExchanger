package com.projects.study.servlet;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.constant.RequestParams;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.InvalidParameterException;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ServletUtils.*;


@WebServlet("/exchangeRate/*")
public class ExchangeRateServlet extends HttpServlet {
    private final ExchangerDao<ExchangeRate> exchangerDao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(exchangerDao);

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        String code = parsePathVar(req);
        String rate = getParamFromBody(req, RequestParams.ER_RATE);
        if (!isValidString(ER_CODE_REGEX, code)) {
            throw new InvalidParameterException(ExceptionMessage.INVALID_ER_CODE);
        } else if (!isValidDecimalInString(rate, ER_MIN_RATE, ER_RATE_REGEX)) {
            throw new InvalidParameterException(String.format(ExceptionMessage.FORMATTED_INVALID_RATE, ER_MIN_RATE));
        } else {
            ExchangeRate updatedRate = exchangeRateService.update(code, new BigDecimal(rate.replace(",", ".")));
            sendResponse(convertToJson(updatedRate), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = parsePathVar(req);
        if (!isValidString(code, ER_CODE_REGEX)) {
            throw new InvalidParameterException(ExceptionMessage.INVALID_ER_CODE);
        }
        ExchangeRate rate = exchangeRateService.get(code);
        sendResponse(convertToJson(rate), resp);
    }

}
