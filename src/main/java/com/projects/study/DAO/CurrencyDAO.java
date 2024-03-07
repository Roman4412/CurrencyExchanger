package com.projects.study.DAO;

import com.projects.study.DbConnectionProvider;
import com.projects.study.entity.Currency;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static com.projects.study.constant.ColumnLabels.*;
import static com.projects.study.constant.SqlQueryConstants.CUR_GET_BY_ID;

public class CurrencyDAO implements DAO<Currency> {
    private static CurrencyDAO currencyDAO;

    private CurrencyDAO() {
    }

    public static CurrencyDAO getInstance() {
        if (currencyDAO == null) {
            currencyDAO = new CurrencyDAO();
        }
        return currencyDAO;
    }

    @Override
    public Optional<Currency> getById(long id) {
        Currency currency = null;

        try (Connection connection = DbConnectionProvider.get();
             PreparedStatement pStmt = connection.prepareStatement(CUR_GET_BY_ID);) {

            pStmt.setLong(1, id);
            ResultSet resultSet = pStmt.executeQuery();

            while (resultSet.next()) {
                currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.ofNullable(currency);
    }

    @Override
    public List<Currency> getAll() {
        return null;
    }

    @Override
    public void save(Currency currency) {

    }

    @Override
    public void delete(long id) {

    }
}
