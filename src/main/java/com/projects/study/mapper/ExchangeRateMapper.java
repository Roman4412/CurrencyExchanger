package com.projects.study.mapper;

import com.projects.study.dao.CurrencyDao;
import com.projects.study.dao.Dao;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.service.CurrencyService;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.projects.study.ControllerUtils.formatParam;
import static com.projects.study.constant.ColumnLabels.*;
import static com.projects.study.constant.ColumnLabels.RATES_RATE;

public class ExchangeRateMapper implements ExchangerMapper<ExchangeRate> {
    private final Dao<Currency> currencyDao = CurrencyDao.getInstance();
    private final CurrencyService currencyService = new CurrencyService(currencyDao);

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
                System.out.println(rate);
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
        String baseCurCode = formatParam(params.get("baseCurCode")[0]);
        String targetCurCode = formatParam(params.get("targetCurCode")[0]);
        String rate = params.get("rate")[0];

        ExchangeRate newExchangeRate = new ExchangeRate();
        Currency baseCur = currencyService.getByCode(baseCurCode);
        Currency targetCur = currencyService.getByCode(targetCurCode);
        newExchangeRate.setBaseCurrency(baseCur);
        newExchangeRate.setTargetCurrency(targetCur);
        newExchangeRate.setRate(new BigDecimal(rate));
        return newExchangeRate;
    }

}