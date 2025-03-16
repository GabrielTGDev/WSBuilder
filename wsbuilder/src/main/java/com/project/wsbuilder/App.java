package com.project.wsbuilder;

import com.project.wsbuilder.models.UserModel;
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

    public static Scene getScene() {
        return scene;
    }

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if an error occurs during application startup
     */
    @Override
    public void start(Stage stage) throws IOException {
        if (!launched) {
            scene = new Scene(loadFXML("authentication"), 700, 400);
            stage.setScene(scene);
            stage.setResizable(false); // Deshabilita el redimensionamiento
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
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        if ("mainview".equals(fxml)) {
            scene.getWindow().setWidth(UserModel.isLoggedIn() ? 1074 : 1024);
            scene.getWindow().setHeight(600);
        } else {
            scene.getWindow().sizeToScene(); // Ajustar el tamaño de la ventana al contenido
        }
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