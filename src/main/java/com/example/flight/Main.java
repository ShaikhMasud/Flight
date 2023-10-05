package com.example.flight;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class Main extends Application {

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.primaryStage = primaryStage;

        // Load the login form initially
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the login page
        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Add a method to switch to the registration page
    public void showRegistration() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        RegistrationController registrationController = loader.getController();
        registrationController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Registration");
    }

    // Add a method to switch back to the login page
    public void showLogin() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the login page
        LoginController loginController = loader.getController();
        loginController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
    }

    public void showHome() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        HomeController homeController = loader.getController();
        homeController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Home");
    }

    public void showBookingPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketbooking.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the booking page
        TicketBookingController ticketBookingController = loader.getController();
        ticketBookingController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Page");
    }


    public void showCancelPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        BookingCancelController bookingCancelController = loader.getController();
        bookingCancelController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Booking Cancel Page");
    }

    public void showUpdatePage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("updateprofile.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        UpdateProfileController updateProfileController = loader.getController();
        updateProfileController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Update Page");
    }


    public void showForgotPassword() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("forgot_password.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        ForgotPasswordController forgotPasswordController = loader.getController();
        forgotPasswordController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Forgot Password Page");
    }

    public void showPayment() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
        Scene scene = new Scene(loader.load());

        // Set the controller for the registration page
        PaymentController paymentController = loader.getController();
        paymentController.setMainApp(this);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Payment");
    }
}


