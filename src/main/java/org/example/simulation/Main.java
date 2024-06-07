package org.example.simulation;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/application.fxml")));
        primaryStage.setTitle("Epidemic Simulator");
        primaryStage.setScene(new Scene(root));

        String stylesPath = Objects.requireNonNull(this.getClass().getResource("/styles.css")).toExternalForm();
        root.getStylesheets().add(stylesPath);

        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icon.png")));
        primaryStage.getIcons().add(icon);

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
