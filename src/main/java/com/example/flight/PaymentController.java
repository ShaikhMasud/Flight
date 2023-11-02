package com.example.flight;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
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

        if (cardNumber.isEmpty() || cardName.isEmpty() || cvvCode.isEmpty()) {
            showError("Please fill in all required fields.");
        } else if (!isValidCardNumber(cardNumber) || !isValidCardName(cardName) || !isValidCvvCode(cvvCode)) {
            showError("Invalid card information. Please check the fields.");
        } else {
            // If card information is valid, insert booking details and payment details
            boolean bookingSuccess = insertBookingDetails(selectedFlight);
            boolean paymentSuccess = insertPaymentDetails(selectedFlight, cardNumber);

            if (bookingSuccess && paymentSuccess) {
                PaymentDone_label.setText("Payment and booking successfully completed!");
            } else {
                showError("Failed to insert booking and payment details.");
            }
        }
    }

    // Insert booking details into the 'Booking' table
    private boolean insertBookingDetails(FlightSearch selectedFlight) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO booking(user_id,ticket_id, flight_id,flight_name,leave_airport,destination_airport, date, arrival_time,departure_time,price,booking_datetime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)"
            );

            java.util.Date utilDate = selectedFlight.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
            // Set the appropriate values for user_id, ticket_id, flight_id, and booking_datetime
            statement.setInt(1, Integer.parseInt(loggedInUserId));
            statement.setInt(2, Integer.parseInt(tf_ticketid.getText()));
            statement.setInt(3, selectedFlight.getFlight_id());
            statement.setString(4, selectedFlight.getFlight_name());
            statement.setString(5, selectedFlight.getLeave());
            statement.setString(6, selectedFlight.getDestination());
            statement.setDate(7, sqlDate); // Use the converted java.sql.Date
            statement.setString(8, selectedFlight.getArrival_time()); // Use the converted java.sql.Timestamp
            statement.setString(9, selectedFlight.getDeparture_time()); // Use the converted java.sql.Timestamp
            statement.setDouble(10, selectedFlight.getPrice()); // Assuming price is an integer
            statement.setTimestamp(11, new Timestamp(System.currentTimeMillis()));

            // Execute the SQL statement
            int rowsInserted = statement.executeUpdate();

            // Check if the insert was successful
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Insert payment details into the 'Payment' table
    private boolean insertPaymentDetails(FlightSearch selectedFlight, String cardNumber) {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = DatabaseConnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO Payment (booking_id, payment_date, amount) VALUES (?, ?, ?)"
            );

            // Get the booking_id (assuming you have a way to obtain it after the booking)
            int bookingId = getBookingId();

            // Set the appropriate values for booking_id, payment_date, and amount
            statement.setInt(1, bookingId);
            statement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            statement.setDouble(3, selectedFlight.getPrice());

            // Execute the SQL statement
            int rowsInserted = statement.executeUpdate();

            // Check if the insert was successful
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Add a method to retrieve the booking_id (you should implement this method)
    // Add a method to retrieve the booking_id
    private int getBookingId() {
        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = DatabaseConnection.getConnection();
            // Create a SQL statement to retrieve the last inserted booking_id
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT LAST_INSERT_ID() AS booking_id"
            );

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Retrieve the booking_id from the result set
                return resultSet.getInt("booking_id");
            } else {
                // Handle the case where no booking_id was found
                return -1; // Or use another suitable error value
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed
            return -1; // Or use another suitable error value
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
        // Check if the card number contains 16 digits
        return cardNumber.matches("\\d{16}");
    }


    private boolean isValidCardName(String cardName) {
        // Check if the card name contains only alphabetic characters (no digits or special characters)
        return cardName.matches("^[a-zA-Z ]+$");
    }


    private boolean isValidCvvCode(String cvvCode) {
        // Check if the CVV code has a length of 3 digits
        return cvvCode.length() == 3;
    }


}
