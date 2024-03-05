package com.projects.study;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbConnectionProvider {
    private static final String URL = "jdbc:sqlite:E:/programs/python/Scripts/currency_exchanger_db.db";
    private static Connection connection;
    private DbConnectionProvider() {
    }

    private static void initConnection() {
        if(connection == null) {
            try {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection(URL);
            } catch (SQLException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static Connection get() {
        initConnection();
        return connection;
    }
}