<<<<<<< HEAD
module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;

    opens com.example.view to javafx.graphics, javafx.fxml;
    exports com.example.view;  // <-- Adicione esta linha
}
=======
module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;

    opens com.example.view to javafx.graphics, javafx.fxml;
    exports com.example.view;  // <-- Adicione esta linha
}
>>>>>>> 6f16e8280fb6c55f7700b702cefa24b11130a45c
