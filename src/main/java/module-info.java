module org.example.roomplanner {
    requires javafx.controls;
    requires javafx.fxml;
    requires jimObjModelImporterJFX;


    opens org.example.roomplanner.controller to javafx.fxml;
    opens org.example.roomplanner to javafx.fxml;


    exports org.example.roomplanner;
    exports org.example.roomplanner.model;
    opens org.example.roomplanner.model to javafx.fxml;
}