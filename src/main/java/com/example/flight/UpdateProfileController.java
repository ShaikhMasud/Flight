package com.example.flight;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateProfileController implements Initializable {

    @FXML
    private MenuButton BookingPage;

    @FXML
    private Button Button_Update;

    @FXML
    private MenuItem MB_Action1;

    @FXML
    private MenuItem MB_Action2;

    @FXML
    private MenuItem MB_TicketCancel;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuBack;

    @FXML
    private Label UserName;

    @FXML
    private Label label_flightbooking;

    @FXML
    private Label tf_Home;

    @FXML
    private TextField tf_username;
    @FXML
    private TextField tf_firstname;
    @FXML
    private TextField tf_lastname;
    @FXML
    private TextField pf_password;

    @FXML
    private Label label_message;

    @FXML
    private VBox slider;

    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void update() {
        String firstname = tf_firstname.getText();
        String lastname = tf_lastname.getText();
        String username = tf_username.getText();
        String password = pf_password.getText();

        if (updateuser(firstname,lastname,username, password)) {
            label_message.setText("Update successful!");
        } else {
            label_message.setText(" ");
        }
    }


    private boolean updateuser(String firstname, String lastname, String username, String password) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE users SET firstname = ?, lastname = ?, password = ? WHERE username = ?")) {

            // Update the user's profile based on their username
            stmt.setString(1, firstname);
            stmt.setString(2, lastname);
            stmt.setString(3, password);
            stmt.setString(4, username);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        slider.setTranslateX(-176);
        Menu.setOnMouseClicked (event -> { TranslateTransition slide = new TranslateTransition();

            slide.setDuration(Duration.seconds (0.4));

            slide.setNode(slider);

            slide.setToX(0);

            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished ((ActionEvent e)->
            {
                Menu.setVisible(false);
                MenuBack.setVisible(true);

            });

        });
        MenuBack.setOnMouseClicked (event -> { TranslateTransition slide = new TranslateTransition();

            slide.setDuration(Duration.seconds (0.4));

            slide.setNode(slider);

            slide.setToX(-176);

            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished ((ActionEvent e)->
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
            Stage stage = (Stage) tf_Home.getScene().getWindow();
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
    private void GoToBookingCancelPage() {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
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
    private void goToUpdatePage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("updateprofile.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    }



