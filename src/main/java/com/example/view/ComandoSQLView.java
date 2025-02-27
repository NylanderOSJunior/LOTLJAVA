package com.example.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComandoSQLView extends Application {
    private String sqlText;

    public ComandoSQLView(String sqlText) {
        this.sqlText = sqlText; // Recebe o comando SQL a ser exibido
    }

    @Override
    public void start(Stage stage) {
        // Cria um TextArea para exibir o comando SQL
        TextArea textArea = new TextArea();
        textArea.setText(sqlText);  // Define o texto do TextArea com o SQL recebido
        textArea.setWrapText(true);  // Permite a quebra de linha no texto
        textArea.setEditable(false); // Não permite edição do texto

        // Adiciona um botão para fechar a janela
        Button btnClose = new Button("Fechar");
        btnClose.setOnAction(e -> stage.close());  // Fecha a tela quando o botão for pressionado

        // Layout para exibir o comando SQL e o botão de fechamento
        VBox vbox = new VBox(textArea, btnClose);
        vbox.setSpacing(10);
        vbox.getStyleClass().add("main-container");
        
        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Comando SQL Executado");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
