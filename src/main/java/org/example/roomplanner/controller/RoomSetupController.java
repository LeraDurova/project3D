package org.example.roomplanner.controller;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.example.roomplanner.RoomParams;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomSetupController implements Initializable {

    @FXML private AnchorPane rootPane;
    @FXML private VBox card;
    @FXML private TextField lengthField;
    @FXML private TextField widthField;
    @FXML private TextField heightField;
    @FXML private Button nextButton;
    @FXML private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Размер карточки (можно скорректировать)
        card.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.6));
        card.maxHeightProperty().bind(rootPane.heightProperty().multiply(0.5));

        // Биндинг: все поля должны быть непустыми, чтобы разблокировать nextButton
        BooleanBinding allFilled = lengthField.textProperty().isNotEmpty()
                .and(widthField.textProperty().isNotEmpty())
                .and(heightField.textProperty().isNotEmpty());

        nextButton.disableProperty().bind(allFilled.not());
    }


    @FXML
    private void onBackClicked() throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/roomplanner/view/menu.fxml")
        );
        AnchorPane menuPane = loader.load();

        rootPane.getChildren().setAll(menuPane);
        AnchorPane.setTopAnchor(menuPane,    0.0);
        AnchorPane.setBottomAnchor(menuPane, 0.0);
        AnchorPane.setLeftAnchor(menuPane,   0.0);
        AnchorPane.setRightAnchor(menuPane,  0.0);
    }


    @FXML
    private void onNextClicked() {
        String lText = lengthField.getText()
                .trim()
                .replace(',', '.')   // запятая → точка
                .replaceAll("\\s+", ""); // убрать все пробелы
        String wText = widthField.getText()
                .trim()
                .replace(',', '.')
                .replaceAll("\\s+", "");
        String hText = heightField.getText()
                .trim()
                .replace(',', '.')
                .replaceAll("\\s+", "");
        double length, width, height;
        try {
            length = Double.parseDouble(lText);
            width  = Double.parseDouble(wText);
            height = Double.parseDouble(hText);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Неверный формат числа");
            alert.setHeaderText("Поле содержит недопустимый формат");
            alert.setContentText("Пожалуйста, вводи числа в формате 3.5 или 3,5 без лишних символов.");
            alert.showAndWait();
            return;
        }

        // Сохраняем параметры
        RoomParams.length = length;
        RoomParams.width  = width;
        RoomParams.height = height;

        // Пытаемся загрузить следующий экран
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/org/example/roomplanner/view/room_design.fxml")
            );
            AnchorPane designPane = loader.load();
            rootPane.getChildren().setAll(designPane);
            AnchorPane.setTopAnchor(designPane,    0.0);
            AnchorPane.setBottomAnchor(designPane, 0.0);
            AnchorPane.setLeftAnchor(designPane,   0.0);
            AnchorPane.setRightAnchor(designPane,  0.0);
        } catch (IOException e) {
            e.printStackTrace(); // для отладки
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Ошибка загрузки экрана");
            alert.setHeaderText("Не удалось перейти к конструированию комнаты");
            alert.setContentText("Проверьте, что файл room_design.fxml лежит в ресурcах по правильному пути.");
            alert.showAndWait();
        }
    }
}