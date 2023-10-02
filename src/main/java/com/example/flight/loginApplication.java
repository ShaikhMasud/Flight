package com.example.flight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class loginApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the login page as the initial scene
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("login.fxml"));
        Parent loginRoot = loginLoader.load();
        Scene loginScene = new Scene(loginRoot);

        primaryStage.setTitle("Login Page");
        primaryStage.setScene(loginScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
