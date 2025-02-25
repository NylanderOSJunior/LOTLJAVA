module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens view to javafx.graphics, javafx.fxml;
    exports view;  // <-- Adicione esta linha
}
