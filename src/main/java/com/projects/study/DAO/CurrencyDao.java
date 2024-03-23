package com.projects.study.DAO;

import com.projects.study.DbConnectionProvider;
import com.projects.study.entity.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.projects.study.constant.ColumnLabels.*;
import static com.projects.study.constant.SqlQueryConstants.*;

public class CurrencyDao implements Dao<Currency> {
    private static CurrencyDao currencyDAO;

    private CurrencyDao() {
    }

    public static CurrencyDao getInstance() {
        if (currencyDAO == null) {
            currencyDAO = new CurrencyDao();
        }
        return currencyDAO;
    }

    @Override
    public Optional<Currency> getByCode(String code) {
        Currency currency = null;

        try(Connection connection = DbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_CODE)) {

            pStmt.setString(1, code);
            ResultSet resultSet = pStmt.executeQuery();

            while(resultSet.next()) {
                currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
            }
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(currency);
    }

    @Override
    public Stream<Currency> getAll() {
        Stream.Builder<Currency> currencies = Stream.builder();
        try(Connection connection = DbConnectionProvider.get();
            Statement stmt = connection.createStatement();
            ResultSet resultSet = stmt.executeQuery(CUR_GET_ALL)) {

            while(resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
                currencies.add(currency);
            }
            return currencies.build();
        } catch(SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency save(Currency currency) {
        try(Connection connection = DbConnectionProvider.get();
            PreparedStatement pStmt = connection.prepareStatement(CUR_SAVE)) {
            Currency newCurrency = currency;
            pStmt.setString(1, newCurrency.getCode());
            pStmt.setString(2, newCurrency.getFullName());
            pStmt.setString(3, newCurrency.getSign());
            pStmt.execute();
            ResultSet resultSet = pStmt.getGeneratedKeys();
            newCurrency.setId(resultSet.getLong(1));
            return newCurrency;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Currency currency) {
        return false;
    }

}
