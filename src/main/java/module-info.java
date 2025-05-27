module org.example.roomplanner {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.roomplanner.controller to javafx.fxml;
    opens org.example.roomplanner to javafx.fxml;


    exports org.example.roomplanner;
}