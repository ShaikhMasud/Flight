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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Objects;
import java.util.ResourceBundle;

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
    private ImageView Menu;

    @FXML
    private ImageView MenuBack;

    @FXML
    private Button PaytmButton;

    @FXML
    private TextField tf_CardName;

    @FXML
    private TextField tf_CardNumber;

    @FXML
    private Label tf_Home;
    @FXML
    private TextField tf_ticketid;

    @FXML
    private TextField tf_cvvcode;

    private static String loggedInUserId;

    private Main mainApp;

    public void displayticketid(String ticketid) {
        tf_ticketid.setText(ticketid);
    }

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);
    }
    @FXML
    private void PaymentDone(ActionEvent event) {

        insertBookingDetails(selectedFlight);

        PaymentDone_label.setText("Payment successfully completed!");
    }


    public void GoToTicketBooking() {
        try {
            mainApp.showBookingPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
