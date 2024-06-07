package org.example.simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.util.Objects;

/**
 * Main class for the Epidemic Simulator application.
 * Extends the Application class from JavaFX to launch the GUI.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     * Loads the FXML layout, sets the scene, and displays the primary stage.
     *
     * @param primaryStage the primary stage for this application
     * @throws Exception if the FXML file or resources cannot be loaded
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/application.fxml")));
        primaryStage.setTitle("Epidemic Simulator"); // Set the title of the window
        primaryStage.setScene(new Scene(root)); // Set the scene to the primary stage

        // Load and apply the CSS stylesheet
        String stylesPath = Objects.requireNonNull(this.getClass().getResource("/styles.css")).toExternalForm();
        root.getStylesheets().add(stylesPath);

        // Load and set the application icon
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.show(); // Display the primary stage
    }

    /**
     * The main method, which launches the JavaFX application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
