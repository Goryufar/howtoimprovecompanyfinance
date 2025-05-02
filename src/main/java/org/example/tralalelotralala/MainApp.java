package org.example.tralalelotralala;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        // Load the FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("scene1.fxml"));
        Parent root = loader.load();

        javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        double width = screenBounds.getWidth() * 0.9;
        double height = screenBounds.getHeight() * 0.9;

        // Create the scene with dynamic size
        Scene scene = new Scene(root, width, height);

        // Set up the stage
        primaryStage.setTitle("Company Manager");
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(false);
        primaryStage.setMaximized(false);
        primaryStage.show();
    }

    public static void switchScene(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApp.class.getResource(fxmlFile));
            Parent root = loader.load();

            javafx.geometry.Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
            double width = screenBounds.getWidth() * 0.9;
            double height = screenBounds.getHeight() * 0.9;
            Scene scene = new Scene(root, width, height);
            primaryStage.setScene(scene);
            primaryStage.setFullScreen(false);
            primaryStage.setMaximized(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
