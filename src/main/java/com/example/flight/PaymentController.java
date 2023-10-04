//package com.example.flight;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.DatePicker;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class PaymentController extends TicketBookingController {
//
//    @FXML
//    private Button BackToBookingPage;
//
//    @FXML
//    private Button CreditButton;
//
//    @FXML
//    private Button DebitButton;
//
//    @FXML
//    private DatePicker ExpiryDate;
//
//    @FXML
//    private Button GPButton;
//
//    @FXML
//    private Label PaymentDone_label;
//
//    @FXML
//    private Button PaytmButton;
//
//    @FXML
//    private TextField tf_CardName;
//
//    @FXML
//    private TextField tf_CardNumber;
//
//    @FXML
//    private Label tf_Home;
//
//    @FXML
//    private TextField tf_cvvcode;
//
//        @FXML
//        private void handlePaymentDone(ActionEvent event) {
//            // Collect the data needed for the booking
//            String firstName = tf_FirstName.getText();
//            String lastName = tf_LastName.getText();
//            String cardNumber = tf_CardNumber.getText();
//            LocalDate expiryDate = ExpiryDate.getValue();
//
//            // Access flight details from TicketBookingController
//            FlightDetails flightDetails = getFlightDetails();
//
//            // Create a database connection and insert the booking record with flight details
//            try (Connection conn = DatabaseConnection.getConnection()) {
//                // Define the SQL query for inserting into the "booking" table
//                String insertQuery = "INSERT INTO booking (first_name, last_name, card_number, expiry_date, flight_id, flight_name) " +
//                        "VALUES (?, ?, ?, ?, ?, ?)";
//
//                // Prepare the SQL statement with parameters
//                PreparedStatement preparedStatement = conn.prepareStatement(insertQuery);
//                preparedStatement.setString(1, firstName);
//                preparedStatement.setString(2, lastName);
//                preparedStatement.setString(3, cardNumber);
//                preparedStatement.setDate(4, java.sql.Date.valueOf(expiryDate));
//                preparedStatement.setInt(5, flightDetails.getFlightId());
//                preparedStatement.setString(6, flightDetails.getFlightName());
//
//                // Execute the SQL statement
//                int rowsAffected = preparedStatement.executeUpdate();
//
//                if (rowsAffected > 0) {
//                    // Booking was successful, you can display a success message or navigate to a confirmation page
//                    PaymentDone_label.setText("Booking Successful!");
//                } else {
//                    // Booking failed, handle the error
//                    PaymentDone_label.setText("Booking Failed!");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                // Handle database connection or SQL query execution errors here
//            }
//        }
//
//
//
//
//
//}
