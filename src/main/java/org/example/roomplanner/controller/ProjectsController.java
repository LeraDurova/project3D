package org.example.roomplanner.controller;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProjectsController implements Initializable {

    @FXML private AnchorPane rootPane;
    @FXML private VBox card;
    @FXML private TableView<Project> projectsTable;
    @FXML private TableColumn<Project, String> nameCol;
    @FXML private TableColumn<Project, String> dateCol;
    @FXML private TableColumn<Project, String> sizeCol;
    @FXML private Button backButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // биндим размер карточки
        card.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.6));
        card.maxHeightProperty().bind(rootPane.heightProperty().multiply(0.7));

        // настраиваем колонки
        nameCol.setCellValueFactory(cell -> cell.getValue().nameProperty());
        dateCol.setCellValueFactory(cell -> cell.getValue().dateProperty());
        sizeCol.setCellValueFactory(cell -> cell.getValue().sizeProperty());

        // пример данных
        projectsTable.getItems().addAll(
                new Project("Комната гостиной", LocalDate.of(2025,5,20), 4.5, 3.0, 2.8),
                new Project("Столовая",           LocalDate.of(2025,5,22), 5.0, 4.0, 3.0)
        );
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

    /** Простой класс-модель проекта */
    public static class Project {
        private final SimpleStringProperty name;
        private final SimpleStringProperty date;
        private final SimpleStringProperty size;

        public Project(String name, LocalDate date, double dx, double dy, double dz) {
            this.name = new SimpleStringProperty(name);
            this.date = new SimpleStringProperty(date.toString());
            this.size = new SimpleStringProperty(
                    String.format("%.1f×%.1f×%.1f", dx, dy, dz)
            );
        }
        public SimpleStringProperty nameProperty() { return name; }
        public SimpleStringProperty dateProperty() { return date; }
        public SimpleStringProperty sizeProperty() { return size; }
    }
}