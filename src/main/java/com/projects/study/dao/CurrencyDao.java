package com.projects.study.dao;

import com.projects.study.constant.ExceptionMessage;
import com.projects.study.entity.Currency;
import com.projects.study.exception.CurrencyAlreadyExistException;
import com.projects.study.mapper.CurrencyMapper;

import java.sql.*;
import java.util.Optional;
import java.util.stream.Stream;

import static com.projects.study.constant.DaoKit.*;

public class CurrencyDao implements ExchangerDao<Currency> {
    private static CurrencyDao currencyDao;
    private static final CurrencyMapper mapper = new CurrencyMapper();

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        if (currencyDao == null) {
            currencyDao = new CurrencyDao();
        }
        return currencyDao;
    }

    @Override
    public Optional<Currency> get(String code) {
        try(Connection connection = ConnectionProvider.get();
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
        try(Connection connection = ConnectionProvider.get();
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
        try(Connection connection = ConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_SAVE)) {
            pStmt.setString(1, currency.getCode());
            pStmt.setString(2, currency.getName());
            pStmt.setString(3, currency.getSign());
            pStmt.execute();
            ResultSet resultSet = pStmt.getGeneratedKeys();
            currency.setId(resultSet.getLong(1));
            return currency;
        } catch(SQLException e) {
            if (e.getErrorCode() == CONSTRAINT_ERR_CODE) {
                throw new CurrencyAlreadyExistException(
                        String.format(ExceptionMessage.CUR_EXIST_FORMATTED, currency.getCode()));
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Currency currency) {
        return false;
    }

    @Override
    public boolean isExist(String code) {
        try(Connection connection = ConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_CODE)) {
            pStmt.setString(1, code);
            return pStmt.executeQuery().next();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
