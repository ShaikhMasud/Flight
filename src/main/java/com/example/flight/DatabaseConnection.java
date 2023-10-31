package com.example.flight;

import java.sql.*;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // Define your MySQL database connection parameters here
    private static final String DB_URL = "jdbc:mysql://localhost:3306/airline";
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

    public static boolean doesUserExist(String username, String contacts) {
        try (Connection connection = getConnection()) {
            String query = "SELECT 1 FROM users WHERE username = ? OR contacts = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, contacts);

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
