package com.projects.study.servlet;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.constant.RequestParams;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.dto.ExchangeResponse;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.InvalidParameterException;
import com.projects.study.service.CurrencyService;
import com.projects.study.service.ExchangeRateService;
import com.projects.study.service.ExchangeService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.math.BigDecimal;

import static com.projects.study.constant.ValidatorKit.*;
import static com.projects.study.util.ServletUtils.*;


@WebServlet("/exchange")
public class ExchangeServlet extends HttpServlet {
    private final ExchangerDao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    private final ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ExchangeService exchangeService = new ExchangeService(exchangeRateService, currencyService);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String from = req.getParameter(RequestParams.EX_FROM);
        String to = req.getParameter(RequestParams.EX_TO);
        String amount = req.getParameter(RequestParams.EX_AMOUNT);

        if (!isValidStringParam(CUR_CODE_REGEX, from, to)) {
            throw new InvalidParameterException(ExceptionMessage.CUR_INVALID_CODE);
        } else if (!isValidDecimalParam(amount, EX_MIN_AMOUNT, EX_AMOUNT_REGEX)) {
            throw new InvalidParameterException(
                    String.format(ExceptionMessage.EX_INVALID_AMOUNT_FORMATTED, EX_MIN_AMOUNT));
        } else {
            BigDecimal amountDec = new BigDecimal(amount.replace(",", "."));
            ExchangeResponse response = exchangeService.exchange(from, to, amountDec);
            sendResponse(convertToJson(response), resp);
        }
    }

}
