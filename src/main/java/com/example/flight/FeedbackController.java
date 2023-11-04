package com.example.flight;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class FeedbackController implements Initializable {
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
    private TextArea emailTextArea;

    @FXML
    private TextArea feedbackTextArea;

    @FXML
    private Label tf_Home;
    @FXML
    private VBox slider;


    @FXML
    private Label usernamecopy;



    public void displayname(String username) {
        usernamecopy.setText(username);
    }


    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private Integer loggedInUserId;

    public void setLoggedInUserId(Integer userId) {
        loggedInUserId = userId;
    }

    public void submitFeedback(ActionEvent event) {
        String feedback = feedbackTextArea.getText();
        String email = emailTextArea.getText();


        if (feedback.isEmpty()) {
            // Handle empty feedback.
            showAlert("Please provide your feedback before submitting.");
        } else {
            saveFeedbackToDatabase(feedback, email);
            showAlert("Thank you for your feedback!");
            clearFields();
        }
    }

    private void saveFeedbackToDatabase(String feedback, String email) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String insertQuery = "INSERT INTO feedback (user_id,feedback_text,email) VALUES (?, ?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setInt(1, loggedInUserId);
            preparedStatement.setString(2, feedback);
            preparedStatement.setString(3, email);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors as needed.
        }

        System.out.println("feedback: " + feedback);
        System.out.println("email: " + email);
        System.out.println("loggedInUserId: " + loggedInUserId);

    }


    private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Feedback Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        feedbackTextArea.clear();
        emailTextArea.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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

            String username = usernamecopy.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketbooking.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setLoggedInUserId(loggedInUserId);
            feedbackController.displayname(username);


            feedbackController.setMainApp(mainApp);
            mainApp.showBookingPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToBookingCancelPage() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setLoggedInUserId(loggedInUserId);
            feedbackController.setMainApp(mainApp);
            mainApp.showCancelPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToUpdatePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateprofile.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setLoggedInUserId(loggedInUserId);

            feedbackController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setMainApp(mainApp);
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToFlightStatus() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FlightStatus.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setMainApp(mainApp);
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

            FeedbackController feedbackController = loader.getController();
            feedbackController.setMainApp(mainApp);

            // Pass the logged-in user's ID to the home controller
            feedbackController.displayname(username);
            feedbackController.setLoggedInUserId(UserSession.getLoggedInUserId());

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
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            FeedbackController feedbackController = loader.getController();
            feedbackController.setLoggedInUserId(loggedInUserId);
            feedbackController.setMainApp(mainApp);
            mainApp.showfeedback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

