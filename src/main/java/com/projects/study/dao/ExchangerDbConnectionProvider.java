package com.projects.study.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ExchangerDbConnectionProvider {
    private static final String URL = "jdbc:sqlite:E:/programs/python/Scripts/currency_exchanger_db.db";
    private static Connection connection;

    static {
        loadDriver();
    }

    private ExchangerDbConnectionProvider() {
    }

    private static void initConnection() {
        try {
            connection = DriverManager.getConnection(URL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection get() {
        initConnection();
        return connection;
    }

    private static void loadDriver() {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}