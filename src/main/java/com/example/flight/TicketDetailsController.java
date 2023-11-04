package com.example.flight;

import com.example.flight.BookingDetails;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;

public class TicketDetailsController implements Initializable {

    @FXML
    private TableView<BookingDetails> bookingTableView;

    @FXML
    private TableColumn<BookingDetails, Integer> bookingIdColumn;
    @FXML
    private TableColumn<BookingDetails, Integer> userIdColumn;
    @FXML
    private TableColumn<BookingDetails, Integer> ticketIdColumn;
    @FXML
    private TableColumn<BookingDetails, Integer> flightIdColumn;
    @FXML
    private TableColumn<BookingDetails, String> flightNameColumn;
    @FXML
    private TableColumn<BookingDetails, String> leaveAirportColumn;
    @FXML
    private TableColumn<BookingDetails, String> destinationAirportColumn;
    @FXML
    private TableColumn<BookingDetails, Date> dateColumn;
    @FXML
    private TableColumn<BookingDetails, String> arrivalTimeColumn;
    @FXML
    private TableColumn<BookingDetails, String> departureTimeColumn;
    @FXML
    private TableColumn<BookingDetails, Double> priceColumn;
    @FXML
    private TableColumn<BookingDetails, Date> bookingDatetimeColumn;
    @FXML
    private MenuButton BookingPage;

    @FXML
    private MenuItem MB_Action1;

    @FXML
    private MenuItem MB_TicketCancel;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuBack;

    @FXML
    private VBox slider;

    @FXML
    private Label tf_Home;
    public void displayname(String username) {
        usernamecopy.setText(username);
    }

    @FXML
    private Label usernamecopy;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private int loggedInUserId; // Change to int for user_id

    public void setLoggedInUserId(int userId) {
        loggedInUserId = userId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);

        slider.setTranslateX(-176);

        Menu.setOnMouseClicked(event -> {TranslateTransition slide = new TranslateTransition();

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
        // Fetch and display Booking details
        ObservableList<BookingDetails> bookingData = getBookingDetails(loggedInUserId);
        bookingTableView.setItems(bookingData);
    }

    // Implement this method to retrieve data from your database
    private ObservableList<BookingDetails> getBookingDetails(int userId) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT booking_id,ticket_id,flight_id,flight_name,leave_airport,destination_airport,date,arrival_time,departure_time,price,booking_datetime FROM booking WHERE user_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, UserSession.getLoggedInUserId()); // Use setInt for binding the parameter
            ResultSet resultSet = preparedStatement.executeQuery();

            ObservableList<BookingDetails> bookingDetails = FXCollections.observableArrayList();
            while (resultSet.next()) {
                Integer bookingId = resultSet.getInt("Booking ID");
                Integer ticketId = resultSet.getInt("Ticket ID");
                Integer flightId = resultSet.getInt("Flight ID");
                String flightName = resultSet.getString("Flight Name");
                String leaveAirport = resultSet.getString("leave airport");
                String destinationAirport = resultSet.getString("destination airport");
                Date date = resultSet.getDate("date");
                String arrivalTime = resultSet.getString("arrival time");
                String departureTime = resultSet.getString("departure time");
                Double price = resultSet.getDouble("price");
                Timestamp bookingDatetime = resultSet.getTimestamp("booking datetime");

                BookingDetails bookingDetail = new BookingDetails(
                        bookingId, userId, ticketId, flightId, flightName, leaveAirport,
                        destinationAirport, date, arrivalTime, departureTime, price, bookingDatetime
                );

                bookingDetails.add(bookingDetail);
            }
            return bookingDetails;
        } catch (SQLException e) {
            e.printStackTrace();
            return FXCollections.observableArrayList();
        }
    }

    @FXML
    private void GoToBookingPage(ActionEvent event) {
        try {
            // Load the registration.fxml file

            String username = usernamecopy.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketbooking.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setLoggedInUserId(String.valueOf(loggedInUserId));
            ticketBookingController.displayname(username);

            ticketBookingController.setMainApp(mainApp);
            mainApp.showBookingPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToBookingCancelPage() {
        try {
            String username = usernamecopy.getText();

            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.displayname(username);
            homeController.setLoggedInUserId(Integer.valueOf(String.valueOf(loggedInUserId)));
            homeController.setMainApp(mainApp);
            mainApp.showCancelPage();
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
            homeController.setLoggedInUserId(Integer.valueOf(String.valueOf(loggedInUserId)));

            homeController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToFlightStatus() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightStatus.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showflightstatus();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void gotoHome() {
        try {
            String username = usernamecopy.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);

            // Pass the logged-in user's ID to the home controller
            homeController.displayname(username);
            homeController.setLoggedInUserId(Integer.valueOf(String.valueOf(loggedInUserId)));

            mainApp.showHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GotoFeedback() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Feedback.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setMainApp(mainApp);
            feedbackController.setLoggedInUserId(Integer.valueOf(String.valueOf(loggedInUserId)));

            mainApp.showfeedback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
