package com.example.flight;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField tf_contact;
    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField pf_password;

    @FXML
    private Label label_message;

    @FXML
    private PasswordField pf_comfirm;


    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void register() {
        String firstname = tf_firstname.getText();
        String lastname = tf_lastname.getText();
        String username = tf_username.getText();
        String contactText = tf_contact.getText();
        String password = pf_password.getText();
        String confirmPassword = pf_comfirm.getText();

        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || contactText.isEmpty() || confirmPassword.isEmpty()) {
            label_message.setText("Enter Your Details.");
        } else if (!isNumeric(contactText)) {
            label_message.setText("Contact should only contain numbers.");
        } else if (!isAlpha(firstname) || !isAlpha(lastname)) {
            label_message.setText("First name and last name should contain only alphabetical characters.");
        } else {
            long contact = Long.parseLong(contactText);

            if (usernameExists(username)) {
                label_message.setText("User already exists.");
            } else if (!password.equals(confirmPassword)) {
                label_message.setText("Passwords do not match.");
            } else if (createUser(firstname, lastname, username, contact, password, confirmPassword)) {
                label_message.setText("Registration successful!");
            } else {
                label_message.setText("Registration failed.");
            }
        }
    }


    private boolean isAlpha(String value) {
        // Use a regular expression to check if the string contains only alphabetical characters
        return value.matches("^[a-zA-Z]+$");
    }

    private boolean isNumeric(String str) {
        try {
            Long.parseLong(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }




    private boolean usernameExists(String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM users WHERE username = ?")) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Assume an error occurred to prevent registration
        }
    }

    private boolean createUser(String firstname,String lastname,String username, long contacts,String password,String comfirmpassword) {
        // Implement database user creation here
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(firstname,lastname,username,contacts,password,comfirmpassword) VALUES (?, ?, ?, ?,?,?)")) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, username);
            stmt.setLong(4, contacts);
            stmt.setString(5, password);
            stmt.setString(6, comfirmpassword);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void goToLogin() {
        try {
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
