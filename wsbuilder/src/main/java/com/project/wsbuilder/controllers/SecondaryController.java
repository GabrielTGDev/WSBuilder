package com.project.wsbuilder.controllers;

import java.io.IOException;

import com.project.wsbuilder.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}