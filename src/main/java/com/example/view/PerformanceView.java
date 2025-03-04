package com.example.view;

import com.example.controller.PerformanceController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PerformanceView extends Application {
    private TextArea sqlInput;
    private Button generateIndexesButton;
    private TextArea indexesOutput;

    @Override
    public void start(Stage primaryStage) {
        // Campo de entrada para SQL
        sqlInput = new TextArea();
        sqlInput.setPromptText("Digite sua consulta SQL aqui...");
        sqlInput.setPrefHeight(150);

        // Botão de gerar índices
        generateIndexesButton = new Button("Gerar Índices");

        // Campo de saída para os índices sugeridos
        indexesOutput = new TextArea();
        indexesOutput.setEditable(false);
        indexesOutput.setPromptText("Os índices sugeridos aparecerão aqui...");
        indexesOutput.setPrefHeight(150);

        // Layout
        VBox layout = new VBox(10, sqlInput, generateIndexesButton, indexesOutput);
        layout.setPadding(new javafx.geometry.Insets(10));

        // Configuração da cena
        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setTitle("Analisador de Índices");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Criando o controller e associando evento ao botão
        PerformanceController controller = new PerformanceController(this);
        generateIndexesButton.setOnAction(e -> controller.handleGenerateIndexes());
    }

    public String getSQLInput() {
        return sqlInput.getText();
    }

    public void setIndexesOutput(String indexes) {
        indexesOutput.setText(indexes);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
