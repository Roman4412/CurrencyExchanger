package com.projects.study.mapper;

import com.projects.study.constant.RequestParams;
import com.projects.study.entity.Currency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static com.projects.study.constant.DaoKit.*;

public class CurrencyMapper implements ExchangerMapper<Currency> {
    @Override
    public Currency toEntity(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                Currency currency = new Currency();
                currency.setId(resultSet.getLong(ID));
                currency.setCode(resultSet.getString(CUR_CODE));
                currency.setFullName(resultSet.getString(CUR_NAME));
                currency.setSign(resultSet.getString(CUR_SIGN));
                return currency;
            } else {
                return null;
            }
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Currency toEntity(Map<String, String[]> params) {
        String code = params.get(RequestParams.CUR_CODE)[0].toUpperCase();
        String name = params.get(RequestParams.CUR_NAME)[0];
        String sign = params.get(RequestParams.CUR_SIGN)[0].toUpperCase();

        Currency currency = new Currency();
        currency.setCode(code);
        currency.setFullName(name);
        currency.setSign(sign);
        return currency;
    }

}
