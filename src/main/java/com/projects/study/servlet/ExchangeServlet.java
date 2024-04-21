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
    ExchangerDao<ExchangeRate> exchangeRateDao = ExchangeRateDao.getInstance();
    ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateDao);
    ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);
    ExchangeService exchangeService = new ExchangeService(exchangeRateService, currencyService);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        String from = req.getParameter(RequestParams.EX_FROM);
        String to = req.getParameter(RequestParams.EX_TO);
        String amount = req.getParameter(RequestParams.EX_AMOUNT);

        if (!isValidString(CUR_CODE_REGEX, from, to)) {
            throw new InvalidParameterException(ExceptionMessage.INVALID_CURRENCY_CODE);
        } else if (!isValidDecimalInString(amount, EX_MIN_AMOUNT, EX_AMOUNT_REGEX)) {
            throw new InvalidParameterException(
                    String.format(ExceptionMessage.FORMATTED_INVALID_AMOUNT, EX_MIN_AMOUNT));
        } else {
            BigDecimal amountDec = new BigDecimal(amount.replace(",", "."));
            BigDecimal converted = exchangeService.exchange(from, to, amountDec);
            ExchangeResponse response = new ExchangeResponse();
            response.setExchangeRate(exchangeRateService.get(from + to));
            response.setAmount(amountDec);
            response.setConvertedAmount(converted);
            sendResponse(convertToJson(response), resp);
        }
    }

}
