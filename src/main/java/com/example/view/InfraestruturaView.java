package com.example.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;
import javafx.stage.Stage;

public class InfraestruturaView extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Criar botão de voltar para a Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> voltarParaHome(primaryStage));

        // Layout
        StackPane layout = new StackPane();
        layout.getChildren().addAll(new Label("Informações de Infraestrutura"), btnVoltar);

        // Posiciona o botão no canto inferior direito
        StackPane.setAlignment(btnVoltar, Pos.TOP_RIGHT);

        Scene scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Infraestrutura");
        primaryStage.show();
    }

    private void voltarParaHome(Stage primaryStage) {
        // Carregar a tela home
        new HomeView().start(primaryStage);
    }
}
