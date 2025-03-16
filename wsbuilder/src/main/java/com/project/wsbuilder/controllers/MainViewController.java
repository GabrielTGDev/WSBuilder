package com.project.wsbuilder.controllers;

import com.project.wsbuilder.App;
import com.project.wsbuilder.models.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class MainViewController {

    private static final HBox[] COLUMN_TITLES = {
            createColumnTitle(" Trending", "/com/project/wsbuilder/files/images/trending-icon.png"),
            createColumnTitle(" Recommended", "/com/project/wsbuilder/files/images/download-icon.png"),
            createColumnTitle(" Rated configurations", "/com/project/wsbuilder/files/images/star-icon.png"),
            createColumnTitle("Your configurations", null)
    };

    private static HBox createColumnTitle(String text, String imagePath) {
        Label label = new Label(text);
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER); // Centra horizontalmente el contenido
        if (imagePath != null) {
            ImageView imageView = createImageView(imagePath, label);
            hbox.getChildren().addAll(imageView, label);
        } else {
            hbox.getChildren().add(label);
        }
        return hbox;
    }

    private static ImageView createImageView(String imagePath, Label label) {
        ImageView imageView = new ImageView(new Image(MainViewController.class.getResource(imagePath).toExternalForm()));
        double textHeight = label.getFont().getSize();
        imageView.setFitHeight(textHeight); // Ajusta la altura al tamaño del texto
        imageView.setFitWidth(textHeight);  // Ajusta el ancho al tamaño del texto
        return imageView;
    }

    @FXML
    private ImageView homeIcon;

    @FXML
    private ImageView helpIcon;

    @FXML
    private HBox contentBox;

    @FXML
    private HBox menu;

    @FXML
    public void initialize() {
        loadIcons();
        setupMenu();
        loadContent();
    }

    private void loadIcons() {
        homeIcon.setImage(new Image(getClass().getResource("/com/project/wsbuilder/files/images/menu/home_selected.png").toExternalForm()));
        helpIcon.setImage(new Image(getClass().getResource("/com/project/wsbuilder/files/images/help-icon.png").toExternalForm()));
    }

    private void setupMenu() {
        // Añadir botones al menú según el tipo de usuario
        if (isGuestUser()) {
            addMenuButton("home", "home_selected.png");
            addMenuButton("edit", "edit.png");
            addMenuButton("login", "login.png");
            addMenuButton("tool", "tool.png");
        } else {
            addMenuButton("home", "home_selected.png");
            addMenuButton("edit", "edit.png");
            addMenuButton("user", "user.png");
            addMenuButton("logout", "logout.png");
            addMenuButton("tool", "tool.png");
        }
    }

    private void addMenuButton(String action, String iconName) {
        String menuIconPath = "/com/project/wsbuilder/files/images/menu/" + iconName;
        ImageView button = new ImageView(new Image(getClass().getResource(menuIconPath).toExternalForm()));
        button.getStyleClass().add("menu-button");
        button.setOnMouseClicked(event -> handleMenuAction(action));
        menu.getChildren().add(button);
    }

    private void handleMenuAction(String action) {
        try {
            switch (action) {
                case "home":
                    App.setRoot("mainview");
                    break;
                case "edit":
                    // Lógica para cambiar a la vista de edición
                    break;
                case "login":
                    openAuthenticationView();
                    break;
                case "user":
                    // Lógica para cambiar a la vista de usuario
                    break;
                case "logout":
                    UserModel.logout();
                    App.setRoot("authentication");
                    break;
                case "tool":
                    // Lógica para cambiar a la vista de herramientas
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openAuthenticationView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("authentication.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(new Scene(root));

            // Centrar el modal en la ventana principal
            Stage primaryStage = (Stage) App.getScene().getWindow();
            stage.setOnShown(event -> {
                stage.setX(primaryStage.getX() + (primaryStage.getWidth() - stage.getWidth()) / 2);
                stage.setY(primaryStage.getY() + (primaryStage.getHeight() - stage.getHeight()) / 2);
            });

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadContent() {
        // Cargar columnas según el tipo de usuario
        int columnCount = isGuestUser() ? 3 : 4;
        for (int i = 0; i < columnCount; i++) {
            VBox column = new VBox();
            column.setPrefWidth(isGuestUser() ? 330 : 256);
            column.setPrefHeight(578);
            column.getStyleClass().add("column");

            Label columnTitle = new Label();
            columnTitle.setGraphic(COLUMN_TITLES[i]);
            columnTitle.getStyleClass().add("column-title");
            column.getChildren().add(columnTitle);

            String[] medalImages = {
                    "/com/project/wsbuilder/files/images/gold-medal.png",
                    "/com/project/wsbuilder/files/images/silver-medal.png",
                    "/com/project/wsbuilder/files/images/bronze-medal.png"
            };

            for (int j = 0; j < 7; j++) {
                HBox item = new HBox();
                item.setPrefWidth(column.getPrefWidth() - 10);
                item.getStyleClass().add("item-content");

                if (j < 3) {
                    ImageView icon = new ImageView(new Image(getClass().getResource(medalImages[j]).toExternalForm()));
                    item.getChildren().add(icon);
                }

                Label itemLabel = new Label("Random words");
                item.getChildren().add(itemLabel);

                column.getChildren().add(item);
            }

            contentBox.getChildren().add(column);
        }
    }

    private boolean isGuestUser() {
        return !UserModel.isLoggedIn();
    }

}