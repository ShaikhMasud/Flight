module Flight{
    requires javafx.graphics;
   // requires mysql.connector.j;
    requires javafx.fxml;
    requires java.sql;
    requires com.dlsc.formsfx;
    requires javafx.controls;
    requires java.desktop;


    opens com.example.flight to javafx.fxml;
    exports com.example.flight;
}