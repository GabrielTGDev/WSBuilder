package com.project.wsbuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    /**
     * The scene of the application.
     */
    private static Scene scene;
    /**
     * The launched status of the application.
     */
    private static boolean launched = false;

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an error occurs during application startup
     */
    @Override
    public void start(Stage stage) throws IOException {
        if (!launched) {
            scene = new Scene(loadFXML("primary"), 640, 480);
            stage.setScene(scene);
            stage.show();
            launched = true;
        }
    }

    /**
     * Launches the JavaFX application.
     */
    public static boolean isLaunched() {
        return launched;
    }

    /**
     * Sets the root of the scene.
     *
     * @param fxml the name of the FXML file to load
     * @throws IOException if an error occurs during scene root change
     */
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    /**
     * Loads an FXML file.
     *
     * @param fxml the name of the FXML file to load
     * @return the parent node of the FXML file
     * @throws IOException if an error occurs during FXML file loading
     */
    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        if (fxmlLoader.getLocation() == null) {
            throw new IOException("FXML file not found: " + fxml + ".fxml");
        }
        return fxmlLoader.load();
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch();
    }

}