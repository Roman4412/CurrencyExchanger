package com.projects.study.DAO;

import com.projects.study.DbConnectionProvider;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.projects.study.constant.ColumnLabels.*;
import static com.projects.study.constant.SqlQueryConstants.*;

public class ExchangeRateDao implements Dao<ExchangeRate> {

    private static ExchangeRateDao exchangeRateDAO;

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        if (exchangeRateDAO == null) {
            exchangeRateDAO = new ExchangeRateDao();
        }
        return exchangeRateDAO;
    }

    @Override
    public Optional<ExchangeRate> getByCode(String code) {
        return Optional.empty();
    }

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try (Connection connection = DbConnectionProvider.get();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(RATES_GET_ALL)) {

            while (resultSet.next()) {
                ExchangeRate exchangeRate = new ExchangeRate();
                Currency bCurrency = new Currency();
                Currency tCurrency = new Currency();

                exchangeRate.setId(resultSet.getLong(ID));

                bCurrency.setId(resultSet.getLong(RATES_BASE_CUR_ID));
                bCurrency.setCode(resultSet.getString(RATES_BASE_CUR_CODE));
                bCurrency.setFullName(resultSet.getString(RATES_BASE_CUR_NAME));
                bCurrency.setSign(resultSet.getString(RATES_BASE_CUR_SIGN));

                tCurrency.setId(resultSet.getLong(RATES_TARGET_CUR_ID));
                tCurrency.setCode(resultSet.getString(RATES_TARGET_CUR_CODE));
                tCurrency.setFullName(resultSet.getString(RATES_TARGET_CUR_NAME));
                tCurrency.setSign(resultSet.getString(RATES_TARGET_CUR_SIGN));
                exchangeRate.setRate(new BigDecimal(resultSet.getString(RATES_RATE)));
                exchangeRate.setBaseCurrency(bCurrency);
                exchangeRate.setTargetCurrency(tCurrency);
                exchangeRates.add(exchangeRate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return exchangeRates;
    }


    @Override
    public Optional<ExchangeRate> save(ExchangeRate exchangeRate) {
        return Optional.ofNullable(new ExchangeRate());
    }

    @Override
    public Optional<ExchangeRate> delete(long id) {
        return Optional.ofNullable(new ExchangeRate());
    }

}
