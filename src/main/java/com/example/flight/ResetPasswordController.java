package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ResetPasswordController {

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private Button Reset_Button;
    @FXML
    private TextField Username_tf;

    private String loggedInUserId; // Store the logged-in user's ID

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void ResetButton(ActionEvent event) {
        String newpassword = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String username = Username_tf.getText();


        // Verify that newPassword matches confirmPassword.
        if (newpassword.equals(confirmPassword)) {
            // Passwords match; proceed to update the password in the database.
            boolean passwordUpdated = updatepassword( newpassword, username);

            if (passwordUpdated) {
                // Password updated successfully.
                showAlert("Password successfully reset.");
            } else {
                // An error occurred while updating the password.
                showAlert("Password reset failed. Please try again.");
            }
        } else {
            // Passwords do not match.
            showAlert("Passwords do not match. Please try again.");
        }
    }



    private boolean updatepassword(String newpassword, String username) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET password = ? WHERE username = ?")) {

            // Update the user's profile based on their username
            stmt.setString(1, newpassword);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Password Reset");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) Username_tf.getScene().getWindow();
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
