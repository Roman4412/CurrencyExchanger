package com.projects.study.dao;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionProvider {
    public static final String SQLITE_PREFIX = "jdbc:sqlite:";
    public static final String SQLITE_DRIVER = "org.sqlite.JDBC";
    public static final String CURRENCY_EXCHANGER_DB = "/currency_exchanger_db.db";
    private static final HikariDataSource ds = new HikariDataSource();

    static {
        String path = ConnectionProvider.class.getResource(CURRENCY_EXCHANGER_DB).getPath();
        ds.setDriverClassName(SQLITE_DRIVER);
        ds.setJdbcUrl(SQLITE_PREFIX + path);
    }

    private ConnectionProvider() {
    }

    public static Connection get() {
        try {
            return ds.getConnection();
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

}