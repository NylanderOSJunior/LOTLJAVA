module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;

    opens com.example.view to javafx.graphics, javafx.fxml;
    exports com.example.view;  // <-- Adicione esta linha
}
