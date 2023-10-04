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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Objects;
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
    private TextField tf_flightid;

    @FXML
    private TextField tf_leave;

    @FXML
    private Label usernamecopy;


    private Main mainApp;

    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;
    }

    public void displayname(String username) {
        usernamecopy.setText(username);
    }

    public void displayleave(String leave){
        tf_leaveSearch.setText(leave);
    }
    public void displaydestination(String destination){
        tf_destinationSearch.setText(destination);
    }



    ObservableList<FlightSearch>flightSearchObservableList= FXCollections.observableArrayList();
    Integer index;

//    @FXML
//    void getDetails(MouseEvent event) {
//        index = flighttableview.getSelectionModel().getSelectedIndex();
//
//        if (index <= -1){
//            return;
//        }
//
//        tf_flightid.setText(TableCol_Flightid.getCellData(index).toString());
//        tf_flightName.setText(TableCol_FlightName.getCellData(index).toString());
//        tf_leave.setText(TableCol_Leave.getCellData(index).toString());
//        tf_Destination.setText(TableCol_Destination.getCellData(index).toString());
//        tf_Date.setText(TableCol_Date.getCellData(index).toString());
//        tf_ArrivalTime.setText(TableCol_ArrivalTime.getCellData(index).toString());
//        tf_DepartureTime.setText(TableCol_DepartureTime.getCellData(index).toString());
//        tf_Price.setText(TableCol_FlightPrice.getCellData(index).toString());
//
//
//    }





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Image image1 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        Menu.setImage(image1);

        Image image2 = new Image(Objects.requireNonNull(getClass().getResource("/Images/Menu.jpg")).toString());
        MenuBack.setImage(image2);

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String flightViewQuery="SELECT flight_id, flight_name, `leave`, destination, date, arrival_time, departure_time, price\n" +
                "FROM flight_details;";

        TableCol_Date.setCellFactory(column -> {
            return new TableCell<FlightSearch, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
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
                Integer queryflight_id= queryOutput.getInt("flight_id");
                String queryflight_name= queryOutput.getString("flight_name");
                String queryleave= queryOutput.getString("leave");
                String querydestination= queryOutput.getString("destination");
                Date querydate= queryOutput.getDate("date");
                Time queryarrival_time= queryOutput.getTime("arrival_time");
                Time querydeparture_time= queryOutput.getTime("departure_time");
                Integer queryprice= queryOutput.getInt("price");

                flightSearchObservableList.add(new FlightSearch(queryflight_id,queryflight_name,queryleave,querydestination,querydate,queryarrival_time,querydeparture_time,queryprice));

            }

            TableCol_Flightid.setCellValueFactory(new PropertyValueFactory<>("flight_id"));
            TableCol_FlightName.setCellValueFactory(new PropertyValueFactory<>("flight_name"));
            TableCol_Leave.setCellValueFactory(new PropertyValueFactory<>("leave"));
            TableCol_Destination.setCellValueFactory(new PropertyValueFactory<>("destination"));
            TableCol_ArrivalTime.setCellValueFactory(new PropertyValueFactory<>("date"));
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

    public void goToHome(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("HomePage.fxml"));
            Stage stage = (Stage) UserName.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            HomeController homeController = loader.getController();
            homeController.setMainApp(mainApp);
            mainApp.showHome();
        }catch (IOException e) {
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
            ticketBookingController.setMainApp(mainApp);
            mainApp.showUpdatePage();
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




        // Check if TableCol_FlightPrice contains an Integer or any numeric type and convert it to a string.
        Object priceValue = TableCol_FlightPrice.getCellData(index);
        String priceString = (priceValue instanceof Number) ? String.valueOf(priceValue) : "";

        tf_Price.setText(priceString);
    }


    @FXML
    private void GoToPayment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Payment.fxml"));
            Stage stage = (Stage) usernamecopy.getScene().getWindow();
            stage.getScene().setRoot(loader.load());

            // Set the controller for the registration page
            TicketBookingController ticketBookingController = loader.getController();
            ticketBookingController.setMainApp(mainApp);
            mainApp.showUpdatePage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

