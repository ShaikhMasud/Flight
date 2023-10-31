package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ForgotPasswordController {

    @FXML
    private TextField usernameField;

    @FXML
    private TextField contactField;

    private String loggedInUserId; // Store the logged-in user's ID

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    @FXML
    private void sendResetLink(ActionEvent event) {
        String username = usernameField.getText();
        String contact = contactField.getText();

        boolean userExists = DatabaseConnection.doesUserExist(username, contact);

        if (userExists) {
            loadResetPasswordForm();
        } else {
            showAlert("User not found. Please check your information and try again.");
        }
    }

    private void loadResetPasswordForm() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            ResetPasswordController resetPasswordController = loader.getController();
            resetPasswordController.setMainApp(mainApp);
            mainApp.showresetpassword();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void goToLogin() {
        try {
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
