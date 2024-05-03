package com.projects.study.constant;

public final class DaoKit {
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
    public static final String RATE_GET_BY_CODE = """
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
    public static final String RATE_SAVE = """
            INSERT INTO exchange_rates (base_currency_id, target_currency_id, rate)
              SELECT
                  (SELECT id FROM currencies WHERE code LIKE ?),
                  (SELECT id FROM currencies WHERE code LIKE ?),
                  ?;
            """;
    public static final String RATE_UPDATE = """
            UPDATE exchange_rates
            SET rate = ?
            WHERE id = ?
            """;
    public static final String ID = "id";
    public static final String CUR_CODE = "code";
    public static final String CUR_NAME = "full_name";
    public static final String CUR_SIGN = "sign";

    public static final String RATES_BASE_CUR_ID = "base_id";
    public static final String RATES_BASE_CUR_CODE = "base_code";
    public static final String RATES_BASE_CUR_NAME = "base_name";
    public static final String RATES_BASE_CUR_SIGN = "base_sign";
    public static final String RATES_TARGET_CUR_ID = "target_id";
    public static final String RATES_TARGET_CUR_CODE = "target_code";
    public static final String RATES_TARGET_CUR_NAME = "target_name";
    public static final String RATES_TARGET_CUR_SIGN = "target_sign";
    public static final String RATES_RATE = "rate";

    public static final int CONSTRAINT_ERR_CODE = 19;

    private DaoKit() {
    }

}