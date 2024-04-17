package com.projects.study.mapper;

import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.CurrencyService;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.projects.study.util.ControllerUtils.formatParam;
import static com.projects.study.constant.DaoKit.*;

public class ExchangeRateMapper implements ExchangerMapper<ExchangeRate> {
    private final CurrencyService currencyService;

    public ExchangeRateMapper(CurrencyService currencyService) {
        this.currencyService = currencyService;

    }

    @Override
    public ExchangeRate toEntity(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                Currency bCurrency = new Currency();
                Currency tCurrency = new Currency();
                ExchangeRate rate = new ExchangeRate();

                bCurrency.setId(resultSet.getLong(RATES_BASE_CUR_ID));
                bCurrency.setCode(resultSet.getString(RATES_BASE_CUR_CODE));
                bCurrency.setFullName(resultSet.getString(RATES_BASE_CUR_NAME));
                bCurrency.setSign(resultSet.getString(RATES_BASE_CUR_SIGN));

                tCurrency.setId(resultSet.getLong(RATES_TARGET_CUR_ID));
                tCurrency.setCode(resultSet.getString(RATES_TARGET_CUR_CODE));
                tCurrency.setFullName(resultSet.getString(RATES_TARGET_CUR_NAME));
                tCurrency.setSign(resultSet.getString(RATES_TARGET_CUR_SIGN));

                rate.setId(resultSet.getLong(ID));
                rate.setRate(new BigDecimal(resultSet.getString(RATES_RATE)));
                rate.setBaseCurrency(bCurrency);
                rate.setTargetCurrency(tCurrency);
                return rate;
            } else {
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ExchangeRate toEntity(Map<String, String[]> params) {
        String baseCurCode = formatParam(params.get("baseCurrencyCode")[0]).toUpperCase().trim();
        String targetCurCode = formatParam(params.get("targetCurrencyCode")[0].toUpperCase().trim());
        String rate = params.get("rate")[0].replace(',', '.')
                .trim();

        ExchangeRate newExchangeRate = new ExchangeRate();
        Currency baseCur = currencyService.get(baseCurCode);
        Currency targetCur = currencyService.get(targetCurCode);
        newExchangeRate.setBaseCurrency(baseCur);
        newExchangeRate.setTargetCurrency(targetCur);
        newExchangeRate.setRate(new BigDecimal(rate));
        return newExchangeRate;
    }

}
