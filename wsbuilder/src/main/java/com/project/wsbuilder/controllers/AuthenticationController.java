package com.project.wsbuilder.controllers;

import com.project.wsbuilder.App;
import com.project.wsbuilder.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthenticationController {

    @FXML
    private VBox guestUserSection;

    @FXML
    private VBox loginSection;

    @FXML
    private VBox signInSection;

    @FXML
    private ImageView helpIcon;

    @FXML
    private TextField loginUsernameField;

    @FXML
    private PasswordField loginPasswordField;

    @FXML
    private TextField signInUsernameField;

    @FXML
    private TextField signInEmailField;

    @FXML
    private PasswordField signInPasswordField;

    @FXML
    private Label loginErrorLabel;

    @FXML
    private Label signInErrorLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Button signInButton;

    private boolean isDatabaseConnected() {
        try {
            // Aquí deberías poner tu lógica para verificar la conexión a la base de datos
            // Por ejemplo, una simple consulta SQL
            return UserModel.checkDatabaseConnection();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    public void initialize() {
        loadSvgIcon();
        addEnterKeyHandler();
        if (!isDatabaseConnected()) {
            disableFormComponents();
        }
    }

    private void loadSvgIcon() {
        String svgPath = "/com/project/wsbuilder/files/images/help-icon.png";
        Image image = new Image(getClass().getResource(svgPath).toExternalForm());
        helpIcon.setImage(image);
    }

    private void addEnterKeyHandler() {
        loginUsernameField.setOnKeyPressed(this::handleEnterKey);
        loginPasswordField.setOnKeyPressed(this::handleEnterKey);
        signInUsernameField.setOnKeyPressed(this::handleEnterKey);
        signInEmailField.setOnKeyPressed(this::handleEnterKey);
        signInPasswordField.setOnKeyPressed(this::handleEnterKey);
    }

    private void handleEnterKey(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            if (event.getSource() == loginUsernameField || event.getSource() == loginPasswordField) {
                handleLogin();
            } else if (event.getSource() == signInUsernameField || event.getSource() == signInEmailField || event.getSource() == signInPasswordField) {
                handleSignIn();
            }
        }
    }

    @FXML
    private void handleLogin() {
        String username = loginUsernameField.getText();
        String password = loginPasswordField.getText();
        if (UserModel.authenticate(username, password)) {
            try {
                App.setRoot("mainview");
                closeModal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            loginErrorLabel.setText("Invalid username or password.");
        }
    }

    @FXML
    private void handleSignIn() {
        String username = signInUsernameField.getText();
        String email = signInEmailField.getText();
        String password = signInPasswordField.getText();
        if (UserModel.register(username, email, password)) {
            try {
                App.setRoot("mainview");
                closeModal();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            signInErrorLabel.setText("User already exists or registration failed. Try again.");
        }
    }

    @FXML
    private void switchToMainView() {
        try {
            App.setRoot("mainview");
            if (UserModel.isLoggedIn()) {
                App.getScene().getWindow().setWidth(App.getScene().getWindow().getWidth() + 50);
            }
            closeModal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeModal() {
        if (loginUsernameField.getScene() != null) {
            Stage stage = (Stage) loginUsernameField.getScene().getWindow();
            stage.close();
        }
    }

    private void disableFormComponents() {
        loginUsernameField.setDisable(true);
        loginPasswordField.setDisable(true);
        signInUsernameField.setDisable(true);
        signInEmailField.setDisable(true);
        signInPasswordField.setDisable(true);
        loginErrorLabel.setText("Database connection failed. Please try again later.");
        signInErrorLabel.setText("Database connection failed. Please try again later.");

        // Cambiar el color de los componentes a gris oscuro
        String grayStyle = "-fx-background-color: #A9A9A9;";
        loginUsernameField.setStyle(grayStyle);
        loginPasswordField.setStyle(grayStyle);
        signInUsernameField.setStyle(grayStyle);
        signInEmailField.setStyle(grayStyle);
        signInPasswordField.setStyle(grayStyle);

        // Deshabilitar los botones de log in y sign in y cambiar su color a gris oscuro
        loginButton.setDisable(true);
        signInButton.setDisable(true);
        String buttonGrayStyle = "-fx-background-color: #A9A9A9;";
        loginButton.setStyle(buttonGrayStyle);
        signInButton.setStyle(buttonGrayStyle);
    }
}