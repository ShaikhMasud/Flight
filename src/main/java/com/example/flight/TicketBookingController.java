package com.example.flight;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TicketBookingController implements Initializable {
    @FXML
    private TextField tf_destinationSearch;

    @FXML
    private TextField tf_leaveSearch;

    @FXML
    private TableColumn<?, ?> TableCol_ArrivalTime;

    @FXML
    private TableColumn<FlightSearch, Date> TableCol_Date;
    @FXML
    private DatePicker DatePicker_Search;


    @FXML
    private TableColumn<?, ?> TableCol_DepartureTime;

    @FXML
    private TableColumn<?, ?> TableCol_Destination;

    @FXML
    private TableColumn<?, ?> TableCol_FlightName;

    @FXML
    private TableColumn<?, ?> TableCol_FlightPrice;

    @FXML
    private TableColumn<?, ?> TableCol_Flightid;

    @FXML
    private TableColumn<?, ?> TableCol_Leave;

    @FXML
    private ImageView Menu;

    @FXML
    private ImageView MenuBack;

    @FXML
    private VBox slider;

    @FXML
    private Label UserName;

    @FXML
    private Label label_flightbooking;

    @FXML
    private TableView<FlightSearch> flighttableview;

    @FXML
    private Button Button_BackToHome;
    @FXML
    private Label tf_Home;

    @FXML
    private TextField tf_ArrivalTime;

    @FXML
    private TextField tf_Date;

    @FXML
    private TextField tf_DepartureTime;

    @FXML
    private TextField tf_Destination;

    @FXML
    private TextField tf_Price;

    @FXML
    private TextField tf_flightName;

    @FXML
    private TextField tf_ticketid;
    @FXML
    private TextField tf_flightid;

    @FXML
    private TextField tf_leave;

    @FXML
    private Label usernamecopy;

    @FXML
    private Label label_msg;

    @FXML
    private Button PaymentButton;




    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    private static String loggedInUserId;

    public static void setLoggedInUserId(String userId) {
        loggedInUserId = userId;
    }

    public void displayname(String username) {
        usernamecopy.setText(username);
    }

    public void displayleave(String leave) {
        tf_leaveSearch.setText(leave);
    }

    public void displaydestination(String destination) {
        tf_destinationSearch.setText(destination);
    }

    public void displaydate(LocalDate date) {
        DatePicker_Search.setValue(date);
    }


    ObservableList<FlightSearch>flightSearchObservableList= FXCollections.observableArrayList();
    Integer index;



//    private void initializeImages() {
//        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
//        Menu.setImage(image1);
//
//        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
//        MenuBack.setImage(image2);
//    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tf_leaveSearch.setDisable(true);
        tf_destinationSearch.setDisable(true);

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);

        int randomTicketID = generateRandomTicketID();
        tf_ticketid.setText(String.valueOf(randomTicketID));
        // ... Rest of your code for database and table initialization
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String flightViewQuery="SELECT flight_id, flight_name, `leave`, destination, date, arrival_time, departure_time, price\n" +
                "FROM flights;";

        TableCol_Date.setCellFactory(column -> {
            return new TableCell<FlightSearch, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        setText(dateFormat.format(item));
                    }
                }
            };
        });


        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput= statement.executeQuery(flightViewQuery);

            while (queryOutput.next())
            {
//                Integer queryticket_id= queryOutput.getInt("ticket_id");
                Integer queryflight_id= queryOutput.getInt("flight_id");
                String queryflight_name= queryOutput.getString("flight_name");
                String queryleave= queryOutput.getString("leave");
                String querydestination= queryOutput.getString("destination");
                Date querydate= queryOutput.getDate("date");
                String queryarrival_time= queryOutput.getString("arrival_time");
                String querydeparture_time= queryOutput.getString("departure_time");
                Integer queryprice= queryOutput.getInt("price");

//                if(tf_leaveSearch.getText().equals(tf_destinationSearch.getText())){
//                    label_msg.setText("Same Value");
//                }


                flightSearchObservableList.add(new FlightSearch(queryflight_id, queryflight_name, queryleave, querydestination, querydate, queryarrival_time, querydeparture_time, queryprice));


//                flightSearchObservableList.add(new FlightSearch(queryflight_id,queryflight_name,queryleave,querydestination,querydate,queryarrival_time,querydeparture_time,queryprice));

            }

            TableCol_Flightid.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
            TableCol_FlightName.setCellValueFactory(new PropertyValueFactory<>("flight_name"));
            TableCol_Leave.setCellValueFactory(new PropertyValueFactory<>("leave"));
            TableCol_Destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            TableCol_Date.setCellValueFactory(new PropertyValueFactory<>("date"));
            TableCol_ArrivalTime.setCellValueFactory(new PropertyValueFactory<>("arrival_time"));
            TableCol_DepartureTime.setCellValueFactory(new PropertyValueFactory<>("departure_time"));
            TableCol_FlightPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

            flighttableview.setItems(flightSearchObservableList);

            //filterlist
            FilteredList<FlightSearch> filtereddata=new FilteredList<>(flightSearchObservableList,b -> true);
            tf_leaveSearch.textProperty().addListener((observable,oldValue,newValue) -> {
                filtereddata.setPredicate(flightSearch -> {
                    //nosearch display all records
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null){
                        return true;
                    }

                    String searchKeyword1 = newValue.toLowerCase();

                    if (flightSearch.getLeave().toLowerCase().contains(searchKeyword1)) {

                        return true; // Means we found a match in leave
                    }
                    else
                    {
                        return false;
                    }
                });
            });

            tf_destinationSearch.textProperty().addListener((observable,oldValue,newValue) -> {
                filtereddata.setPredicate(flightSearch -> {
                    //nosearch display all records
                    if(newValue.isEmpty()||newValue.isBlank()||newValue==null){
                        return true;
                    }

                    String searchKeyword = newValue.toLowerCase();

                    if (flightSearch.getDestination().toLowerCase().indexOf(searchKeyword) > -1) {

                        return true; // Means we found a match in destination

                    }
                    else
                    {
                        return false;
                    }
                });
            });


            DatePicker_Search.valueProperty().addListener((observable, oldValue, newValue) -> {
                filtereddata.setPredicate(flightSearch -> {
                    if (newValue == null) {
                        return true;
                    }

                    // Convert the selected date to a java.sql.Date
                    java.sql.Date selectedDate = java.sql.Date.valueOf(newValue);

                    // Compare the selected date with the date in each FlightSearch object
                    return flightSearch.getDate().equals(selectedDate);
                });
            });





            SortedList<FlightSearch> sortData = new SortedList<>(filtereddata);

            //bind sorted result with table view
            sortData.comparatorProperty().bind(flighttableview.comparatorProperty());
            //apply filter and sorted data to table view
            flighttableview.setItems(sortData);



        }catch (SQLException e) {
        Logger.getLogger(TicketBookingController.class.getName()).log(Level.SEVERE,null,e);
            throw new RuntimeException(e);
        }


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


    @FXML
    private void GoToBookingPage(ActionEvent event) {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ticketbooking.fxml"));
            Stage stage = (Stage) label_flightbooking.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setLoggedInUserId(loggedInUserId);

            ticketBookingController.setMainApp(mainApp);
            mainApp.showBookingPage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToBookingCancelPage(ActionEvent event) {
        try {
            // Load the registration.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingcancel.fxml"));
            Stage stage = (Stage) label_flightbooking.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setLoggedInUserId(loggedInUserId);

            ticketBookingController.setMainApp(mainApp);
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
            TicketBookingController ticketBookingController = loader.getController();
            TicketBookingController.setLoggedInUserId(loggedInUserId);

            ticketBookingController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void GoToPayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.
                    load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            setLoggedInUserId(loggedInUserId);

            ticketBookingController.setMainApp(mainApp);
            mainApp.showPayment();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public void getDetails(javafx.scene.input.MouseEvent mouseEvent) {
        index = flighttableview.getSelectionModel().getSelectedIndex();

        if (index <= -1) {
            return;
        }

        tf_flightid.setText(Objects.toString(TableCol_Flightid.getCellData(index), ""));
        tf_flightName.setText(Objects.toString(TableCol_FlightName.getCellData(index), ""));
        tf_leave.setText(Objects.toString(TableCol_Leave.getCellData(index), ""));
        tf_Destination.setText(Objects.toString(TableCol_Destination.getCellData(index), ""));
        tf_Date.setText(Objects.toString(TableCol_Date.getCellData(index),""));
        tf_ArrivalTime.setText(Objects.toString(TableCol_ArrivalTime.getCellData(index),""));
        tf_DepartureTime.setText(Objects.toString(TableCol_DepartureTime.getCellData(index),""));
        tf_Price.setText(Objects.toString(TableCol_FlightPrice.getCellData(index),""));


    }



    private int generateRandomTicketID() {
        Random random = new Random();
        return random.nextInt(9000) + 1000;
    }



    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setMainApp(mainApp);
            mainApp.showLogin();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private void BookButton(ActionEvent event) throws IOException {
        try {
            String ticketid = tf_ticketid.getText();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            Scene scene = new Scene(root);

            // Get a reference to the PaymentController instance created by the FXMLLoader
            PaymentController paymentController = loader.getController();

            // Pass the selected flight details to PaymentController
            FlightSearch selectedFlight = flighttableview.getSelectionModel().getSelectedItem();
            if (selectedFlight != null) {
                paymentController.setFlightDetails(selectedFlight);
            }

            // Pass the logged-in user ID to PaymentController
            paymentController.displayticketid(ticketid);
            PaymentController.setLoggedInUserId(loggedInUserId);

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Insert booking details into the MySQL database
    public void insertBookingDetails(FlightSearch flightDetails) {

        try {
            DatabaseConnection connectNow = new DatabaseConnection();
            Connection connectDB = DatabaseConnection.getConnection();

            // Define the SQL query to insert booking details
            String insertQuery = "INSERT INTO booking(user_id,ticket_id, flight_id,flight_name,leave_airport,destination_airport, date, arrival_time,departure_time,price,booking_datetime) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?,?,?,?)";

            // Create a PreparedStatement
            PreparedStatement preparedStatement = connectDB.prepareStatement(insertQuery);

// Assuming flightDetails.getArrival_time() and flightDetails.getDeparture_time() return java.sql.Time objects
//            java.sql.Time arrivalTime = flightDetails.getArrival_time();
//            java.sql.Time departureTime = flightDetails.getDeparture_time();

// Convert java.sql.Time to java.util.Date and then to java.sql.Timestamp
//            java.util.Date utilArrivalTime = new java.util.Date(arrivalTime.getTime());
//            java.sql.Timestamp sqlArrivalTime = new java.sql.Timestamp(utilArrivalTime.getTime());
//
//            java.util.Date utilDepartureTime = new java.util.Date(departureTime.getTime());
//            java.sql.Timestamp sqlDepartureTime = new java.sql.Timestamp(utilDepartureTime.getTime());

            java.util.Date utilDate = flightDetails.getDate();
            java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

// Set the parameter values
            preparedStatement.setString(1, UserSession.getLoggedInUserId());
            preparedStatement.setInt(2, Integer.parseInt(tf_ticketid.getText())); // Assuming tf_ticketid is the ticket ID
            preparedStatement.setInt(3, flightDetails.getFlight_id());
            preparedStatement.setString(4, flightDetails.getFlight_name());
            preparedStatement.setString(5, flightDetails.getLeave());
            preparedStatement.setString(6, flightDetails.getDestination());
            preparedStatement.setDate(7, sqlDate); // Use the converted java.sql.Date
            preparedStatement.setString(8, flightDetails.getArrival_time()); // Use the converted java.sql.Timestamp
            preparedStatement.setString(9, flightDetails.getDeparture_time()); // Use the converted java.sql.Timestamp
            preparedStatement.setDouble(10, flightDetails.getPrice()); // Assuming price is an integer
            preparedStatement.setTimestamp(11, java.sql.Timestamp.valueOf(LocalDateTime.now()));



            // Execute the query to insert data
            preparedStatement.executeUpdate();

            // Close the PreparedStatement and database connection
            preparedStatement.close();
            connectDB.close();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database-related errors here
        }
    }


    @FXML
    private void gotoPayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Stage stage = (Stage) tf_Home.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);

            // Pass the logged-in user's ID to the home controller
            homeController.setLoggedInUserId(loggedInUserId); // Set the logged-in user's ID

            mainApp.showHome();
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
            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showflightstatus();
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
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setLoggedInUserId(loggedInUserId);
            ticketBookingController.setMainApp(mainApp);
            mainApp.showfeedback();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

