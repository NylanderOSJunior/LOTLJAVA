module com.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.management;

    opens com.example.view to javafx.graphics, javafx.fxml;
    opens com.example.model to javafx.base; // 🔥 Adicione esta linha para permitir o acesso à classe Sessao
    
    exports com.example.view;
}
