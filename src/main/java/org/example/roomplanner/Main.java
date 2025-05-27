package org.example.roomplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // 1) Загружаем FXML главного меню
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/org/example/roomplanner/view/menu.fxml")
        );
        Scene scene = new Scene(loader.load());

        // 2) Подключаем общий CSS
        scene.getStylesheets().add(
                getClass().getResource("/org/example/roomplanner/styles/styles.css")
                        .toExternalForm()
        );

        // 3) Настраиваем окно
        primaryStage.setTitle("3D Планировщик комнаты 🏠");
        primaryStage.setScene(scene);
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(500);   // минимальные размеры
        primaryStage.setMinHeight(600);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}