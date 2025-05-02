module org.example.tralalelotralala {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;

    opens org.example.tralalelotralala to javafx.fxml;
    exports org.example.tralalelotralala;
}