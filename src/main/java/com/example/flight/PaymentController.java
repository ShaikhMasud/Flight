package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
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

    private Connection connection;

    // Initialize the database connection in your controller
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);
    }

    @FXML
    private void PaymentDone() {
        String cardNumber = tf_CardNumber.getText();
        String cardName = tf_CardName.getText();
        String cvvCode = tf_cvvcode.getText();
        String ticketid = tf_ticketid.getText();


        if (cardNumber.isEmpty() || cardName.isEmpty() || cvvCode.isEmpty()) {
            showError("Please fill in all required fields.");
        } else if (!isValidCardNumber(cardNumber) || !isValidCardName(cardName) || !isValidCvvCode(cvvCode)) {
            showError("Invalid card information. Please check the fields.");
        } else if(ticketidexists(ticketid)) {
            PaymentDone_label.setText("Payment Already Done!");
        } else {
            // If card information is valid, insert booking details
           insertBookingDetails(selectedFlight);

            PaymentDone_label.setText("Payment Successful!");
        }
    }

    private boolean insertBookingDetails(FlightSearch selectedFlight) {
        try {
            String insertQuery = "INSERT INTO booking(user_id, ticket_id, flight_id, flight_name, leave_airport, destination_airport, date, arrival_time, departure_time, price, booking_datetime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection connectDB = DatabaseConnection.getConnection();
                 PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery)) {

                java.util.Date utilDate = selectedFlight.getDate();
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

                preparedStatement.setString(1, UserSession.getLoggedInUserId());
                preparedStatement.setInt(2, Integer.parseInt(tf_ticketid.getText()));
                preparedStatement.setInt(3, selectedFlight.getFlight_id());
                preparedStatement.setString(4, selectedFlight.getFlight_name());
                preparedStatement.setString(5, selectedFlight.getLeave());
                preparedStatement.setString(6, selectedFlight.getDestination());
                preparedStatement.setDate(7, sqlDate);
                preparedStatement.setString(8, selectedFlight.getArrival_time());
                preparedStatement.setString(9, selectedFlight.getDeparture_time());
                preparedStatement.setDouble(10, selectedFlight.getPrice());
                preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(LocalDateTime.now()));

                int rowsInserted = preparedStatement.executeUpdate();

                return rowsInserted > 0; // Return true if at least one row was inserted
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidCardNumber(String cardNumber) {
        return cardNumber.matches("\\d{16}");
    }

    private boolean isValidCardName(String cardName) {
        return cardName.matches("^[a-zA-Z ]+$");
    }

    private boolean isValidCvvCode(String cvvCode) {
        return cvvCode.length() == 3;
    }

    @FXML
    private void goToHome() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setMainApp(mainApp);
            ticketBookingController.setLoggedInUserId(loggedInUserId);
            mainApp.showHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean ticketidexists(String ticketid) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM booking WHERE ticket_id = ?")) {
            stmt.setString(1, ticketid);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);
            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return true; // Assume an error occurred to prevent registration
        }
    }

}
