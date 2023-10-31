//import javafx.embed.swing.SwingFXUtils;
//import javafx.scene.Scene;
//import javafx.scene.control.Label;
//import javafx.scene.image.WritableImage;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.paint.Color;
//import javafx.stage.Stage;
//import javafx.stage.Window;
//
//import javax.imageio.ImageIO;
//import java.io.File;
//import java.io.IOException;
//
//public class ReceiptImageGenerator {
//
//    public static void generateReceiptImage(Window owner, String ticketId, String flightId, String destination, String departure, String price) {
//        // Create a new stage for rendering the receipt
//        Stage stage = new Stage();
//        AnchorPane root = new AnchorPane();
//        Scene scene = new Scene(root, 300, 200);
//
//        // Create labels for receipt details
//        Label ticketIdLabel = new Label("Ticket ID: " + ticketId);
//        Label flightIdLabel = new Label("Flight ID: " + flightId);
//        Label destinationLabel = new Label("Destination: " + destination);
//        Label departureLabel = new Label("Departure: " + departure);
//        Label priceLabel = new Label("Price: " + price);
//
//        // Position the labels as needed
//        ticketIdLabel.setLayoutX(20);
//        ticketIdLabel.setLayoutY(20);
//        flightIdLabel.setLayoutX(20);
//        flightIdLabel.setLayoutY(40);
//        destinationLabel.setLayoutX(20);
//        destinationLabel.setLayoutY(60);
//        departureLabel.setLayoutX(20);
//        departureLabel.setLayoutY(80);
//        priceLabel.setLayoutX(20);
//        priceLabel.setLayoutY(100);
//
//        // Add labels to the root layout
//        root.getChildren().addAll(ticketIdLabel, flightIdLabel, destinationLabel, departureLabel, priceLabel);
//
//        // Set a transparent background
//        scene.setFill(Color.TRANSPARENT);
//
//        // Attach the scene to the stage
//        stage.setScene(scene);
//
//        // Take a snapshot of the scene
//        WritableImage snapshot = scene.snapshot(null);
//
//        // Save the snapshot as an image file
//        File outputFile = new File("receipt.png");
//        try {
//            ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", outputFile);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        // Show the receipt image (you can choose to display it to the user or save it as needed)
//        stage.initOwner(owner);
//        stage.show();
//    }
//}
