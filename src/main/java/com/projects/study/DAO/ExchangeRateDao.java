package com.projects.study.DAO;

import com.projects.study.DbConnectionProvider;
import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;

import java.math.BigDecimal;
import java.sql.*;
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
    public Optional<ExchangeRate> getByCode(String curPair) {
        ExchangeRate rate = null;

        String firstCur = curPair.substring(0, 3);
        String secondCur = curPair.substring(3, 6);
        try(Connection connection = DbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_GET_BY_CUR_PAIR)) {

            pStmt.setString(1, firstCur);
            pStmt.setString(2, secondCur);
            ResultSet resultSet = pStmt.executeQuery();

            while(resultSet.next()) {
                Currency bCurrency = new Currency();
                Currency tCurrency = new Currency();
                rate = new ExchangeRate();

                rate.setId(resultSet.getLong(ID));

                bCurrency.setId(resultSet.getLong(RATES_BASE_CUR_ID));
                bCurrency.setCode(resultSet.getString(RATES_BASE_CUR_CODE));
                bCurrency.setFullName(resultSet.getString(RATES_BASE_CUR_NAME));
                bCurrency.setSign(resultSet.getString(RATES_BASE_CUR_SIGN));

                tCurrency.setId(resultSet.getLong(RATES_TARGET_CUR_ID));
                tCurrency.setCode(resultSet.getString(RATES_TARGET_CUR_CODE));
                tCurrency.setFullName(resultSet.getString(RATES_TARGET_CUR_NAME));
                tCurrency.setSign(resultSet.getString(RATES_TARGET_CUR_SIGN));

                rate.setRate(new BigDecimal(resultSet.getString(RATES_RATE)));
                rate.setBaseCurrency(bCurrency);
                rate.setTargetCurrency(tCurrency);

            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(rate);
    }

    @Override
    public List<ExchangeRate> getAll() {
        List<ExchangeRate> exchangeRates = new ArrayList<>();

        try(Connection connection = DbConnectionProvider.get();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(RATES_GET_ALL)) {

            while(resultSet.next()) {
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
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return exchangeRates;
    }


    @Override
    public Optional<ExchangeRate> save(ExchangeRate exchangeRate) {
        try(Connection connection = DbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_SAVE)) {

            pStmt.setString(1, exchangeRate.getBaseCurrency().getCode());
            pStmt.setString(2, exchangeRate.getTargetCurrency().getCode());
            pStmt.setDouble(3, exchangeRate.getRate().doubleValue());
            pStmt.executeUpdate();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(exchangeRate);
    }

    @Override
    public boolean update(ExchangeRate rate) {
        try(Connection connection = DbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_UPDATE)) {

            pStmt.setLong(2, rate.getId());
            pStmt.setDouble(1, rate.getRate().doubleValue());
            return pStmt.executeUpdate() == 1;

        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(long id) {
        return false;
    }

}
