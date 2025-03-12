package com.project.wsbuilder.controllers;

import com.project.wsbuilder.App;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public void initialize() {
        loadSvgIcon();
    }

    private void loadSvgIcon() {
        String svgPath = "/com/project/wsbuilder/files/images/help-icon.png";
        Image image = new Image(getClass().getResource(svgPath).toExternalForm());
        helpIcon.setImage(image);
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