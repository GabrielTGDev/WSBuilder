package com.project.wsbuilder;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;
import org.junit.jupiter.api.*;
import org.testfx.framework.junit5.ApplicationTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the App class.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AppTest extends ApplicationTest {

    private App app;

    /**
     * Sets up the application and starts the JavaFX stage.
     *
     * @param stage the primary stage for this application
     * @throws Exception if an error occurs during application startup
     */
    @Override
    public void start(Stage stage) throws Exception {
        app = new App(); // Create a new instance of the App class
        app.start(stage); // Start the JavaFX application with the given stage
    }

    /**
     * Initializes the App instance before each test.
     */
    @BeforeEach
    void setUp() {
        app = new App();
    }

    /**
     * Verifies that the initial scene is set correctly.
     */
    @Test
    @Order(1)
    void start_initialSceneIsSet() {
        Node root = lookup(".root").query(); // Query the node with the CSS class 'root'
        assertNotNull(root); // Assert that the node is not null
        assertEquals(640, root.getLayoutBounds().getWidth()); // Assert that the node's width is 640
        assertEquals(480, root.getLayoutBounds().getHeight()); // Assert that the node's height is 480
    }

    /**
     * Verifies that a valid FXML file loads successfully.
     *
     * @throws IOException if an error occurs during FXML loading
     */
    @Test
    @Order(2)
    void loadFXML_validFileLoadsSuccessfully() throws IOException {
        Parent root = App.loadFXML("primary"); // Load the FXML file named 'primary.fxml'
        assertNotNull(root); // Assert that the loaded FXML root is not null
    }

    /**
     * Verifies that an IOException is thrown when loading an invalid FXML file.
     */
    @Test
    @Order(3)
    void loadFXML_invalidFileThrowsIOException() {
        // Assert that loading a nonexistent FXML file throws an IOException
        assertThrows(IOException.class, () -> App.loadFXML("nonexistent"));
    }

    /**
     * Verifies that the application launches successfully.
     */
    @Test
    @Order(4)
    void main_applicationLaunchesSuccessfully() {
        if (!App.isLaunched()) { // Check if the application is not launched
            // Assert that launching the application does not throw an exception
            assertDoesNotThrow(() -> App.main(new String[]{}));
        }
        assertTrue(App.isLaunched()); // Assert that the application is launched
    }
}