package com.projects.study.constant;

public class SqlQueryConstants {
    public static final String CUR_GET_BY_CODE = """
            SELECT *
            FROM currencies
            WHERE code LIKE ?
            """;
    public static final String CUR_GET_ALL = """
            SELECT *
            FROM currencies
            """;
    public static final String CUR_SAVE = """
            INSERT INTO currencies (code,full_name,sign)
            VALUES (?,?,?)
            """;

    public static final String RATES_GET_ALL = """
            SELECT er.id,
                   er.base_currency_id AS base_id,
                   baseCurrency.code AS base_code,
                   baseCurrency.full_name AS base_name,
                   baseCurrency.sign AS base_sign,
                   er.target_currency_id AS target_id,
                   targetCurrency.code AS target_code,
                   targetCurrency.full_name AS target_name,
                   targetCurrency.sign AS target_sign,
                   er.rate
            FROM exchange_rates er
                     JOIN currencies baseCurrency ON er.base_currency_id = baseCurrency.id
                     JOIN currencies targetCurrency ON er.target_currency_id = targetCurrency.id;
            """;
    public static final String RATE_GET_BY_CUR_PAIR = """
            SELECT er.id,
                   er.base_currency_id AS base_id,
                   baseCurrency.code AS base_code,
                   baseCurrency.full_name AS base_name,
                   baseCurrency.sign AS base_sign,
                   er.target_currency_id AS target_id,
                   targetCurrency.code AS target_code,
                   targetCurrency.full_name AS target_name,
                   targetCurrency.sign AS target_sign,
                   er.rate
            FROM exchange_rates er
                     JOIN currencies baseCurrency ON er.base_currency_id = baseCurrency.id
                     JOIN currencies targetCurrency ON er.target_currency_id = targetCurrency.id
            WHERE base_code LIKE ? AND target_code LIKE ?;
            """;

}