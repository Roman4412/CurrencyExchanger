package com.projects.study.mapper;

import com.projects.study.constant.RequestParams;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.CurrencyService;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

                bCurrency.setId(resultSet.getLong(ER_BASE_CUR_ID));
                bCurrency.setCode(resultSet.getString(ER_BASE_CUR_CODE));
                bCurrency.setName(resultSet.getString(ER_BASE_CUR_NAME));
                bCurrency.setSign(resultSet.getString(ER_BASE_CUR_SIGN));

                tCurrency.setId(resultSet.getLong(ER_TARGET_CUR_ID));
                tCurrency.setCode(resultSet.getString(ER_TARGET_CUR_CODE));
                tCurrency.setName(resultSet.getString(ER_TARGET_CUR_NAME));
                tCurrency.setSign(resultSet.getString(ER_TARGET_CUR_SIGN));

                rate.setId(resultSet.getLong(ID));
                rate.setRate(new BigDecimal(resultSet.getString(ER_RATE)));
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
        String baseCurCode = params.get(RequestParams.ER_BASE_CUR)[0].toUpperCase();
        String targetCurCode = params.get(RequestParams.ER_TARGET_CUR)[0].toUpperCase();
        String rate = params.get(RequestParams.ER_RATE)[0];

        ExchangeRate newExchangeRate = new ExchangeRate();
        Currency baseCur = currencyService.get(baseCurCode);
        Currency targetCur = currencyService.get(targetCurCode);
        newExchangeRate.setBaseCurrency(baseCur);
        newExchangeRate.setTargetCurrency(targetCur);
        newExchangeRate.setRate(new BigDecimal(rate.replace(",",".")));
        return newExchangeRate;
    }

}
