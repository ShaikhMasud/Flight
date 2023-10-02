package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_username;

    @FXML
    private PasswordField tf_password;

    @FXML
    private Label label_loginmessage;
    @FXML
    private Button button_login;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void login(ActionEvent event) throws IOException{
        String username = tf_username.getText();
        String password = tf_password.getText();

        if (validateLogin(username, password)) {
            label_loginmessage.setText("Login successful!");
            // Implement the logic for what to do after a successful login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            root = loader.load();

            HomeController homeController = loader.getController();
            homeController.displayname(username);

            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            gotoHome();
        } else if (username.isEmpty() || password.isEmpty()) {
            label_loginmessage.setText("Enter Your Username And Password.");
        }
        else {
            label_loginmessage.setText("Invalid credentials.");
        }
    }

    private boolean validateLogin(String username, String password) {
        // Implement database validation here
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @FXML
    private void goToRegistration() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            RegistrationController registrationController = loader.getController();
            registrationController.setMainApp(mainApp);
            mainApp.showRegistration();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoHome(){
        try{
            String username = tf_username.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showHome();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoforgotpassword(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("forgot_password.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            ForgotPasswordController forgotPasswordController = loader.getController();
            forgotPasswordController.setMainApp(mainApp);
            mainApp.showForgotPassword();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }



}
