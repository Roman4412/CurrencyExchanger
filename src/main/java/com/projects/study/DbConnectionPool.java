package com.projects.study;

import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;


public class DbConnectionPool {
    private static HikariDataSource ds;

    private DbConnectionPool() {

    }

    private static void initializeDbConnectionPool() {
        if (ds == null) {
            ds = new HikariDataSource();
            ds.setJdbcUrl(System.getenv("Exchanger_DB_URL"));
        }
    }

    public static Connection getConnection() throws SQLException {
        initializeDbConnectionPool();
        return ds.getConnection();
    }

}