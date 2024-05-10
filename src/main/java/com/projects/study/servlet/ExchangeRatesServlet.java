package com.projects.study.servlet;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.constant.RequestParams;
import com.projects.study.constant.ValidatorKit;
import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.ExchangerDao;
import com.projects.study.dao.ExchangeRateDao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.exception.InvalidParameterException;
import com.projects.study.mapper.ExchangeRateMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;
import com.projects.study.service.ExchangeRateService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import static com.projects.study.util.ServletUtils.*;


@WebServlet("/exchangeRates")
public class ExchangeRatesServlet extends HttpServlet {
    private final ExchangerDao<ExchangeRate> exchangeRateExchangerDao = ExchangeRateDao.getInstance();
    private final ExchangeRateService exchangeRateService = new ExchangeRateService(exchangeRateExchangerDao);
    private final ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ExchangerMapper<ExchangeRate> mapper = new ExchangeRateMapper(currencyService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        List<ExchangeRate> rates = exchangeRateService.getAll();
        sendResponse(convertToJson(rates), resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String baseCurCode = req.getParameter(RequestParams.ER_BASE_CUR);
        String targetCurCode = req.getParameter(RequestParams.ER_TARGET_CUR);
        String rate = req.getParameter(RequestParams.ER_RATE);

        if (!isValidStringParam(ValidatorKit.CUR_CODE_REGEX, baseCurCode, targetCurCode)) {
            throw new InvalidParameterException(ExceptionMessage.CUR_INVALID_CODE);
        } else if (!isValidDecimalParam(rate, ValidatorKit.ER_MIN_RATE, ValidatorKit.ER_RATE_REGEX)) {
            throw new InvalidParameterException(
                    String.format(ExceptionMessage.ER_INVALID_RATE_FORMATTED, ValidatorKit.ER_MIN_RATE));
        } else {
            ExchangeRate savedExchangeRate = exchangeRateService.save(mapper.toEntity(req.getParameterMap()));
            resp.setStatus(HttpServletResponse.SC_CREATED);
            sendResponse(convertToJson(savedExchangeRate), resp);
        }
    }

}
