package com.example.view;

import com.example.controller.InfraestruturaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

public class InfraestruturaView {
    private InfraestruturaController controller = new InfraestruturaController();

    public VBox criarTela(StackPane contentArea) {

        // Criando o layout da tela
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Título
        Label lblTitulo = new Label("Informações da Infraestrutura");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Criando rótulo com informações do sistema
        Label lblInfo = new Label(controller.obterInformacoesSistema());
        lblInfo.setStyle("-fx-font-size: 14px;");

        // Botão para voltar à tela principal
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll(new Label("Bem-vindo à Home!")));

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblTitulo, lblInfo, btnVoltar);

        // Criando o ScrollPane para permitir rolagem se o conteúdo for maior que a tela
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Ajusta à largura da tela
        scrollPane.setPadding(new Insets(10));

        // Criando um VBox para centralizar o ScrollPane na tela
        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
    }
}

