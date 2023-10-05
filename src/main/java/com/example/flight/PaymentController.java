package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class PaymentController extends TicketBookingController {

    @FXML
    private Button BackToBookingPage;

    @FXML
    private Button CreditButton;

    @FXML
    private Button DebitButton;

    @FXML
    private DatePicker ExpiryDate;

    @FXML
    private Button GPButton;

    @FXML
    private Label PaymentDone_label;

    @FXML
    private Button PaytmButton;

    @FXML
    private TextField tf_CardName;

    @FXML
    private TextField tf_CardNumber;

    @FXML
    private Label tf_Home;

    @FXML
    private TextField tf_cvvcode;

    private static String loggedInUserId;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }
    public static void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }

    private FlightSearch selectedFlight;

    public void setFlightDetails(FlightSearch selectedFlight) {
        this.selectedFlight = selectedFlight;
    }

    @FXML
    private void PaymentDone(ActionEvent event) {
        // Handle the payment process (capture card details, etc.)
        // Call the insertBookingDetails method from TicketBookingController
        insertBookingDetails(selectedFlight);

        // Display a confirmation message or navigate to a success page
        PaymentDone_label.setText("Payment successfully completed!");

        // You can navigate back to the booking page or perform other actions here
    }


}
