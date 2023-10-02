package com.example.flight;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
    private PasswordField pf_confirmPassword;
    @FXML
    private Label label_message;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void register() {
        String firstname = tf_firstname.getText();
        String lastname = tf_lastname.getText();
        String username = tf_username.getText();
        String contact = tf_contact.getText();
        String password = pf_password.getText();
        String confirmPassword = pf_confirmPassword.getText();

        if (firstname.isEmpty() || lastname.isEmpty() || username.isEmpty() || contact.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            label_message.setText("All fields are required.");
        } else if (!contact.matches("\\d+")) {
            label_message.setText("Contact should only contain numbers.");
        } else if (!password.equals(confirmPassword)) {
            label_message.setText("Passwords do not match.");
        } else {
            // Attempt to register the user in the database
            if (registerUser(firstname, lastname, username, contact, password)) {
                label_message.setText("Registration successful!");
                clearFields();
            } else {
                label_message.setText("Registration failed. Please try again.");
            }
        }
    }

    private boolean registerUser(String firstname, String lastname, String username, String contact, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users (firstname, lastname, username, contact, password) VALUES (?, ?, ?, ?, ?)")) {
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, username);
            stmt.setString(4, contact);
            stmt.setString(5, password);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearFields() {
        tf_firstname.clear();
        tf_lastname.clear();
        tf_username.clear();
        tf_contact.clear();
        pf_password.clear();
        pf_confirmPassword.clear();
    }

    @FXML
    private void goToLogin() {
        try {
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}