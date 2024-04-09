package com.projects.study.dao;

import com.projects.study.entity.Currency;
import com.projects.study.entity.ExchangeRate;
import com.projects.study.mapper.ExchangeRateMapper;
import com.projects.study.mapper.ExchangerMapper;
import com.projects.study.service.CurrencyService;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

import static com.projects.study.constant.DaoKit.*;

public class ExchangeRateDao implements ExchangerDao<ExchangeRate> {

    private static ExchangeRateDao exchangeRateDAO;
    ExchangerDao<Currency> currencyDao = CurrencyDao.getInstance();
    CurrencyService currencyService = new CurrencyService(currencyDao);
    private final ExchangerMapper<ExchangeRate> mapper = new ExchangeRateMapper(currencyService);

    private ExchangeRateDao() {
    }

    public static ExchangeRateDao getInstance() {
        if (exchangeRateDAO == null) {
            exchangeRateDAO = new ExchangeRateDao();
        }
        return exchangeRateDAO;
    }

    @Override
    public Optional<ExchangeRate> get(String code) {
        String firstCur = code.substring(0, 3);
        String secondCur = code.substring(3, 6);
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_GET_BY_CODE)) {
            pStmt.setString(1, firstCur);
            pStmt.setString(2, secondCur);
            ResultSet resultSet = pStmt.executeQuery();
            return Optional.ofNullable(mapper.toEntity(resultSet));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<ExchangeRate> getAll() {
        Stream.Builder<ExchangeRate> builder = Stream.builder();
        try(Connection connection = ExchangerDbConnectionProvider.get();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(RATES_GET_ALL)) {
            while(resultSet.next()) {
                builder.add(mapper.toEntity(resultSet));
            }
            return builder.build();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public ExchangeRate save(ExchangeRate exchangeRate) {
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_SAVE)) {
            pStmt.setString(1, exchangeRate.getBaseCurrency().getCode());
            pStmt.setString(2, exchangeRate.getTargetCurrency().getCode());
            pStmt.setDouble(3, exchangeRate.getRate().doubleValue());
            pStmt.execute();
            ResultSet resultSet = pStmt.getGeneratedKeys();
            exchangeRate.setId(resultSet.getLong(1));
            return exchangeRate;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(ExchangeRate rate) {
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_UPDATE)) {
            pStmt.setLong(2, rate.getId());
            pStmt.setDouble(1, rate.getRate().doubleValue());
            return pStmt.execute();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean isExist(String code) {
        String firstCur = code.substring(0, 3);
        String secondCur = code.substring(3, 6);
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(RATE_GET_BY_CODE)) {
            pStmt.setString(1, firstCur);
            pStmt.setString(2, secondCur);
            return pStmt.executeQuery().next();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
