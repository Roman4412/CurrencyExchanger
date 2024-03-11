package com.projects.study.DAO;

import com.projects.study.DbConnectionProvider;
import com.projects.study.entity.Currency;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

        try (Connection connection = DbConnectionProvider.get();
             PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_CODE)) {

            pStmt.setString(1, code);
            ResultSet resultSet = pStmt.executeQuery();

            while (resultSet.next()) {
                currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return Optional.ofNullable(currency);
    }

    @Override
    public List<Currency> getAll() {
        List<Currency> currencies = new ArrayList<>();

        try (Connection connection = DbConnectionProvider.get();
             Statement stmt = connection.createStatement();
             ResultSet resultSet = stmt.executeQuery(CUR_GET_ALL)) {

            while (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
                currencies.add(currency);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return currencies;
    }

    @Override
    public Optional<Currency> save(Currency currency) {
        return Optional.ofNullable(new Currency());
    }

    @Override
    public Optional<Currency> delete(long id) {
        return Optional.ofNullable(new Currency());
    }

}
