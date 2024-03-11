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

}