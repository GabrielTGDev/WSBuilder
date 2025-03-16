package com.project.wsbuilder.controllers;

import com.project.wsbuilder.App;
import com.project.wsbuilder.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

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
    public void initialize() {
        loadSvgIcon();
        addEnterKeyHandler();
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
                App.setRoot("primary");
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
                App.setRoot("primary");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            signInErrorLabel.setText("User already exists or registration failed. Try again.");
        }
    }

    @FXML
    private void switchToPrimary() {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}