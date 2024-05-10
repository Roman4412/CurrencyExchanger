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

        System.out.println(rate);
        System.out.println(!isValidDecimalParam(rate, ER_MIN_RATE, ER_RATE_REGEX));
        System.out.println(!isValidStringParam(ER_CODE_REGEX, code));

        if (!isValidStringParam(ER_CODE_REGEX, code)) {
            throw new InvalidParameterException(ExceptionMessage.ER_INVALID_CODE);
        } else if (!isValidDecimalParam(rate, ER_MIN_RATE, ER_RATE_REGEX)) {
            throw new InvalidParameterException(String.format(ExceptionMessage.ER_INVALID_RATE_FORMATTED, ER_MIN_RATE));
        } else {
            ExchangeRate updatedRate = exchangeRateService.update(code, new BigDecimal(rate.replace(",", ".")));
            sendResponse(convertToJson(updatedRate), resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String code = parsePathVar(req);
        if (!isValidStringParam(ER_CODE_REGEX,code)) {
            throw new InvalidParameterException(ExceptionMessage.ER_INVALID_CODE);
        }
        ExchangeRate rate = exchangeRateService.get(code);
        sendResponse(convertToJson(rate), resp);
    }

}
