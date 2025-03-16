package com.project.wsbuilder.controllers;

import com.project.wsbuilder.App;
import com.project.wsbuilder.dbconnection.DatabaseConnection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PrimaryController implements Initializable {

    @FXML
    private Label userInfoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadUserInfo();
    }

    private void loadUserInfo() {
        String query = "SELECT username FROM users.users WHERE id = 1";
        try (DatabaseConnection dbConnection = new DatabaseConnection()) {
            ResultSet resultSet = dbConnection.executeQuery(query);
            if (resultSet.next()) {
                String userInfo = resultSet.getString("username"); // Assuming the column name is 'username'
                userInfoLabel.setText(userInfo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}