package com.example.view;

import com.example.controller.InfraestruturaController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InfraestruturaView {
    private InfraestruturaController controller = new InfraestruturaController();

    public VBox criarTela(StackPane contentArea) {
        // Criando o layout da tela
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        // Criando rótulo com informações do sistema
        Label lblInfo = new Label(controller.obterInformacoesSistema());
        lblInfo.setStyle("-fx-font-size: 14px;");

        // Botão para voltar à tela principal
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll(new Label("Bem-vindo à Home!")));

        // Adiciona os componentes ao layout
        layout.getChildren().addAll(lblInfo, btnVoltar);

        return layout;
    }

}

