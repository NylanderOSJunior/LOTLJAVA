module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.view to javafx.graphics, javafx.fxml;
    exports com.example.view;  // <-- Adicione esta linha
}
