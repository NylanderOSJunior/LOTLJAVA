package com.example.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomeView extends Application {
    private StackPane contentArea;
    private VBox menuLateral;

    @Override
    public void start(Stage primaryStage) {
        // Menu lateral
        menuLateral = new VBox(10);
        menuLateral.setPadding(new Insets(15));
        menuLateral.setStyle("-fx-background-color: #333;");

        Button btnConectar = new Button("Conectar Banco");
        Button btnFuncionalidades = new Button("Funcionalidades");
        Button btnInfra = new Button("Infraestrutura");


        // Aplicando a classe CSS aos botões
        btnConectar.getStyleClass().add("button");
        btnFuncionalidades.getStyleClass().add("button");
        btnInfra.getStyleClass().add("button");


        btnConectar.setOnAction(e -> carregarTela(new ConexaoView(), primaryStage));
        btnFuncionalidades.setOnAction(e -> carregarTela(new FuncionalidadesView(), primaryStage));
        btnInfra.setOnAction(e -> carregarTela(new InfraestruturaView(), primaryStage));

        menuLateral.getChildren().addAll( btnConectar, btnFuncionalidades, btnInfra);

        // Área de conteúdo
        contentArea = new StackPane();
        contentArea.setStyle("-fx-background-color: #f4f4f4; -fx-padding: 20;");

        // Layout principal
        BorderPane layout = new BorderPane();
        layout.setLeft(menuLateral);
        layout.setCenter(contentArea);

        Scene scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Home - Monitoramento Oracle");
        primaryStage.show();
    }

    private void carregarTela(Application tela, Stage primaryStage) {
        try {
            tela.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
