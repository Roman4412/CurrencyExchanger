package com.projects.study.dao;

import com.projects.study.entity.Currency;
import com.projects.study.mapper.CurrencyMapper;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

import static com.projects.study.constant.DaoKit.*;

public class CurrencyDao implements ExchangerDao<Currency> {
    private static CurrencyDao currencyDAO;
    private static final CurrencyMapper mapper = new CurrencyMapper();

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        if (currencyDAO == null) {
            currencyDAO = new CurrencyDao();
        }
        return currencyDAO;
    }

    @Override
    public Optional<Currency> get(String code) {
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_CODE)) {
            pStmt.setString(1, code);
            ResultSet resultSet = pStmt.executeQuery();
            return Optional.ofNullable(mapper.toEntity(resultSet));
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Stream<Currency> getAll() {
        Stream.Builder<Currency> currencies = Stream.builder();
        try(Connection connection = ExchangerDbConnectionProvider.get();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(CUR_GET_ALL)) {
            while(resultSet.next()) {
                currencies.add(mapper.toEntity(resultSet));
            }
            return currencies.build();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency save(Currency currency) {
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_SAVE)) {
            pStmt.setString(1, currency.getCode());
            pStmt.setString(2, currency.getFullName());
            pStmt.setString(3, currency.getSign());
            pStmt.execute();
            ResultSet resultSet = pStmt.getGeneratedKeys();
            currency.setId(resultSet.getLong(ID));
            return currency;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Currency currency) {
        return false;
    }

    @Override
    public boolean isExist(String code) {
        try(Connection connection = ExchangerDbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_CODE)) {
            pStmt.setString(1, code);
            return  pStmt.executeQuery().next();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
