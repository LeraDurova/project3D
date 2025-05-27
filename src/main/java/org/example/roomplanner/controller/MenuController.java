package org.example.roomplanner.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private AnchorPane rootPane;  // корневой контейнер из menu.fxml
    @FXML private VBox card;            // наша голубая «карточка»

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // биндим размер карточки, если нужно (можно убрать или поменять):
        card.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.6));
        card.maxHeightProperty().bind(rootPane.heightProperty().multiply(0.5));
    }

    @FXML
    private void onStartClicked() throws IOException {
        // загружаем экран создания комнаты
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/roomplanner/view/room_setup.fxml")
        );
        AnchorPane roomPane = loader.load();
        // подменяем всё содержимое rootPane на новый экран
        rootPane.getChildren().setAll(roomPane);
        AnchorPane.setTopAnchor(roomPane, 0.0);
        AnchorPane.setBottomAnchor(roomPane, 0.0);
        AnchorPane.setLeftAnchor(roomPane, 0.0);
        AnchorPane.setRightAnchor(roomPane, 0.0);
    }

    @FXML
    private void onMyProjectsClicked() throws IOException {
        // Загружаем projects.fxml по правильному пути
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/roomplanner/view/projects.fxml")
        );
        AnchorPane projPane = loader.load();

        // Заменяем содержимое rootPane
        rootPane.getChildren().setAll(projPane);
        AnchorPane.setTopAnchor(projPane, 0.0);
        AnchorPane.setBottomAnchor(projPane, 0.0);
        AnchorPane.setLeftAnchor(projPane, 0.0);
        AnchorPane.setRightAnchor(projPane, 0.0);
    }
}