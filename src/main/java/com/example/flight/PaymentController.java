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
//    public void Book() {
//        String firstname = .getText();
//        String lastname = tf_lastname.getText();
//        String username = tf_username.getText();
////        int contact = Integer.parseInt(tf_contact.getText());
//        long contact = Long.parseLong(tf_contact.getText());
//
//        String password = pf_password.getText();
//        String confirmPassword = pf_comfirm.getText();
//
//
//        if (username.isEmpty() || password.isEmpty() || firstname.isEmpty() || lastname.isEmpty() || tf_contact.getText().isEmpty() || confirmPassword.isEmpty()) {
//            label_message.setText("Enter Your Details.");
//        }
//        else if (!isNumeric(tf_contact.getText())) {
//            label_message.setText("Contact should only contain numbers.");
//        }
//        else {
//
//            if (usernameExists(username)) {
//                label_message.setText("User already exists.");
//            } else if (!password.equals(confirmPassword)) {
//                label_message.setText("Passwords do not match.");
//            } else if (createUser(firstname, lastname, username, contact, password, confirmPassword)) {
//                label_message.setText("Registration successful!");
//            } else {
//                label_message.setText("Registration failed.");
//            }
//        }
//    }
//
//    private boolean BookTicket(String firstname,String lastname,String username, long contacts,String password,String comfirmpassword) {
//        // Implement database user creation here
//        try (Connection conn = DatabaseConnection.getConnection();
//             PreparedStatement stmt = conn.prepareStatement("INSERT INTO users(firstname,lastname,username,contacts,password,comfirmpassword) VALUES (?, ?, ?, ?,?,?)")) {
//            stmt.setString(1, firstname);
//            stmt.setString(2, lastname);
//            stmt.setString(3, username);
//            stmt.setLong(4, contacts);
//            stmt.setString(5, password);
//            stmt.setString(6, comfirmpassword);
//            int rowsAffected = stmt.executeUpdate();
//            return rowsAffected == 1;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//}
