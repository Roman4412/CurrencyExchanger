package com.projects.study.dao;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class ConnectionProvider {
    private static HikariDataSource ds;
    public static final String DRIVER_NAME = "org.sqlite.JDBC";
    public static final String DB_URL = "EXCH_DB";

    static {
        ds = new HikariDataSource();
        ds.setDriverClassName(DRIVER_NAME);
        ds.setJdbcUrl(System.getenv(DB_URL));
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