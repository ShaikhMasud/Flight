package com.example.flight;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.stage.Stage;

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
        // Clear any previous highlighting
        clearFieldStyles();

        String firstname = tf_firstname.getText();
        String lastname = tf_lastname.getText();
        String username = tf_username.getText();
        String contactText = tf_contact.getText();
        String password = pf_password.getText();
        String confirmPassword = pf_comfirm.getText();

        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || contactText.isEmpty() || confirmPassword.isEmpty()) {
            label_message.setText("Enter Your Details.");
            highlightEmptyFields();
        } else if (!isNumeric(contactText) || contactText.length() != 10) {
            label_message.setText("Contact should be a 10-digit number.");
            highlightTextField(tf_contact);
        } else if (!isAlpha(firstname) || !isAlpha(lastname)) {
            label_message.setText("First name and last name should contain only alphabetical characters.");
            if (!isAlpha(firstname)) {
                highlightTextField(tf_firstname);
            }
            if (!isAlpha(lastname)) {
                highlightTextField(tf_lastname);
            }
        } else {
            long contact = Long.parseLong(contactText);

            if (usernameExists(username)) {
                label_message.setText("User already exists.");
                highlightTextField(tf_username);
            } else if (!password.equals(confirmPassword)) {
                label_message.setText("Passwords do not match.");
                highlightTextField(pf_password);
                highlightTextField(pf_comfirm);
            } else if (createUser(firstname, lastname, username, contact, password, confirmPassword)) {
                label_message.setText("Registration successful!");
            } else {
                label_message.setText("Registration failed.");
            }
        }
    }

    private void highlightTextField(TextField textField) {
        textField.setStyle("-fx-border-color: red;");
    }

    private void highlightEmptyFields() {
        if (tf_firstname.getText().isEmpty()) {
            highlightTextField(tf_firstname);
        }
        if (tf_lastname.getText().isEmpty()) {
            highlightTextField(tf_lastname);
        }
        if (tf_username.getText().isEmpty()) {
            highlightTextField(tf_username);
        }
        if (tf_contact.getText().isEmpty()) {
            highlightTextField(tf_contact);
        }
        if (pf_password.getText().isEmpty()) {
            highlightTextField(pf_password);
        }
        if (pf_comfirm.getText().isEmpty()) {
            highlightTextField(pf_comfirm);
        }
    }

    private void clearFieldStyles() {
        tf_firstname.setStyle("");  // Clear the style to remove any highlighting
        tf_lastname.setStyle("");
        tf_username.setStyle("");
        tf_contact.setStyle("");
        pf_password.setStyle("");
        pf_comfirm.setStyle("");
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

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}