package com.projects.study.dao;


import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class ExchangerDbConnectionProvider {
    private static final String URL = "jdbc:sqlite:E:/programs/python/Scripts/currency_exchanger_db.db";
    private static final HikariDataSource ds = new HikariDataSource();

    static {
        ds.setDriverClassName("org.sqlite.JDBC");
        ds.setJdbcUrl(URL);
        ds.setMaximumPoolSize(15);
    }

    private ExchangerDbConnectionProvider() {
    }

    public static Connection get() {
        try {
            return ds.getConnection();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}