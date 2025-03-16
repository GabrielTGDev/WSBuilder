package com.project.wsbuilder.controllers;

                import com.project.wsbuilder.App;
                import com.project.wsbuilder.models.UserModel;
                import com.project.wsbuilder.vscapi.VSCodeApiRequest;
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
                import org.json.JSONArray;
                import org.json.JSONObject;

                import java.io.IOException;
                import java.util.ArrayList;
                import java.util.Comparator;
                import java.util.List;

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

                            if (i == 0) {
                                loadTrendingExtensions(column);
                            } else if (i == 1) {
                                loadRecommendedExtensions(column);
                            } else {
                                loadDefaultContent(column);
                            }

                            contentBox.getChildren().add(column);
                        }
                    }

                    private void loadTrendingExtensions(VBox column) {
                        VSCodeApiRequest apiRequest = new VSCodeApiRequest("trending");
                        String response = apiRequest.getExtensions();
                        if (response != null) {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray results = jsonResponse.getJSONArray("results");
                            if (results.length() > 0) {
                                JSONArray extensions = results.getJSONObject(0).getJSONArray("extensions");
                                List<JSONObject> trendingExtensions = new ArrayList<>();
                                for (int i = 0; i < extensions.length(); i++) {
                                    trendingExtensions.add(extensions.getJSONObject(i));
                                }
                                trendingExtensions.sort(Comparator.comparingInt(ext -> {
                                    JSONArray statistics = ext.getJSONArray("statistics");
                                    for (int j = 0; j < statistics.length(); j++) {
                                        JSONObject stat = statistics.getJSONObject(j);
                                        if (stat.has("trendingdaily")) {
                                            return stat.getInt("trendingdaily");
                                        }
                                    }
                                    return 0;
                                }));
                                for (int i = 0; i < Math.min(6, trendingExtensions.size()); i++) {
                                    JSONObject extension = trendingExtensions.get(i);
                                    Label itemLabel = new Label(extension.getString("displayName"));
                                    itemLabel.getStyleClass().add("item-content");
                                    column.getChildren().add(itemLabel);
                                }
                            }
                        }
                    }

                    private void loadRecommendedExtensions(VBox column) {
                        VSCodeApiRequest apiRequest = new VSCodeApiRequest("recommended");
                        String response = apiRequest.getExtensions();
                        if (response != null) {
                            JSONObject jsonResponse = new JSONObject(response);
                            JSONArray results = jsonResponse.getJSONArray("results");
                            if (results.length() > 0) {
                                JSONArray extensions = results.getJSONObject(0).getJSONArray("extensions");
                                List<JSONObject> recommendedExtensions = new ArrayList<>();
                                for (int i = 0; i < extensions.length(); i++) {
                                    recommendedExtensions.add(extensions.getJSONObject(i));
                                }
                                recommendedExtensions.sort((ext1, ext2) -> {
                                    int install1 = 0, install2 = 0;
                                    JSONArray statistics1 = ext1.getJSONArray("statistics");
                                    JSONArray statistics2 = ext2.getJSONArray("statistics");
                                    for (int j = 0; j < statistics1.length(); j++) {
                                        JSONObject stat = statistics1.getJSONObject(j);
                                        if (stat.has("install")) {
                                            install1 = stat.getInt("install");
                                            break;
                                        }
                                    }
                                    for (int j = 0; j < statistics2.length(); j++) {
                                        JSONObject stat = statistics2.getJSONObject(j);
                                        if (stat.has("install")) {
                                            install2 = stat.getInt("install");
                                            break;
                                        }
                                    }
                                    return Integer.compare(install2, install1);
                                });
                                for (int i = 0; i < Math.min(6, recommendedExtensions.size()); i++) {
                                    JSONObject extension = recommendedExtensions.get(i);
                                    Label itemLabel = new Label(extension.getString("displayName"));
                                    itemLabel.getStyleClass().add("item-content");
                                    column.getChildren().add(itemLabel);
                                }
                            }
                        }
                    }

                    private void loadDefaultContent(VBox column) {
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
                    }

                    private boolean isGuestUser() {
                        return !UserModel.isLoggedIn();
                    }

                }