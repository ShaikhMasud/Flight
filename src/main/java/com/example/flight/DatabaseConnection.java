package com.example.flight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Define your MySQL database connection parameters here
    private static final String DB_URL = "jdbc:mysql://localhost:3306/flight";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Hak@786";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Failed to connect to the database", e);
        }
    }
}
