package com.example.flight;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class FlightStatus implements Initializable {

    @FXML
    private Button Button_BackToHome;

    @FXML
    private Button Button_CancelTicket;

    @FXML
    private ImageView Image_BookCancel;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuBack;

    @FXML
    private Label UserName;

    @FXML
    private Label label_flight;

    @FXML
    private TextField tf_date;

    @FXML
    private TextField tf_destination;

    @FXML
    private TextField tf_flightid;

    @FXML
    private TextField tf_leave;

    @FXML
    private TextField tf_price;

    @FXML
    private TextField tf_ticketid;

    @FXML
    private Label label_msg;

    @FXML
    private TextField tf_username;

    @FXML
    private Label usernamecopy;

    @FXML
    private VBox slider;

    private Main mainApp;
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private String loggedInUserId;

    public void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }


    @FXML
    void show(ActionEvent event) {
        String ticketid = tf_ticketid.getText();

        if (ticketid.isEmpty()) {
            label_msg.setText("Enter a Ticket ID.");
        } else {
            // Call a method to retrieve data based on the ticket ID
            BookingData bookingData = retrieveBookingData(ticketid);

            if (bookingData != null) {
                // Populate the text fields with the retrieved data
                tf_flightid.setText(bookingData.getFlightId());
                tf_username.setText(bookingData.getUsername());
                tf_price.setText(bookingData.getPrice());
                tf_leave.setText(bookingData.getLeave());
                tf_destination.setText(bookingData.getDestination());
                tf_date.setText(bookingData.getBookingdate());

//                label_msg.setText("Data retrieved successfully.");
            } else if (ticketid.isEmpty()) {
                label_msg.setText("Enter Ticket Id");
            } else {
                label_msg.setText("Ticket ID not found.");
            }
        }

    }

    private BookingData retrieveBookingData(String booking_id) {
        // Implement database query to retrieve booking data based on ticket ID
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT flight_id, user_id, leave_airport, destination_airport, price,booking_datetime FROM booking WHERE booking_id = ?")) {
            stmt.setString(1, booking_id);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                String flightId = resultSet.getString("flight_id");
                String userId = resultSet.getString("user_id");
                String leave = resultSet.getString("leave_airport");
                String destination = resultSet.getString("destination_airport");
                String price = resultSet.getString("price");
                String bookingdate = resultSet.getString("booking_datetime");


                // Create a BookingData object to store the retrieved data
                return new BookingData(flightId, userId, leave, destination, price,bookingdate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image = new Image(Objects.requireNonNull(getClass().getResource("/Images/TCImage.jpg")).toString());
        MenuBack.setImage(image);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);


        slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();

            slide.setDuration(Duration.seconds(0.4));

            slide.setNode(slider);

            slide.setToX(0);

            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) ->
            {
                Menu.setVisible(false);
                MenuBack.setVisible(true);

            });

        });
        MenuBack.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();

            slide.setDuration(Duration.seconds(0.4));

            slide.setNode(slider);

            slide.setToX(-176);

            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) ->
            {
                Menu.setVisible(true);
                MenuBack.setVisible(false);

            });

        });
    }



    @FXML
    private void GoToBookingPage(ActionEvent event) {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketbooking.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showBookingPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToUpdatePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateprofile.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToBookingCancelPage() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showCancelPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) label_flight.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            BookingCancelController bookingCancelController = loader.getController();
            bookingCancelController.setMainApp(mainApp);
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void GoToFlightStatus() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightStatus.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showflightstatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void goToHome(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showHome();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GotoFeedback() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
            Stage stage = (Stage) tf_username.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FlightStatus flightStatus = loader.getController();
            flightStatus.setLoggedInUserId(loggedInUserId);
            flightStatus.setMainApp(mainApp);
            mainApp.showfeedback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
