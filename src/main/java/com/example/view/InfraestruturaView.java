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
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Informações da Infraestrutura");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label lblInfo = new Label(controller.obterInformacoesSistema());
        lblInfo.setStyle("-fx-font-size: 14px;");

        // Botão para gerar PDF
        Button btnGerarPDF = new Button("Exportar como PDF");

        // Botão para voltar à Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll(new Label("Bem-vindo à Home!")));

        layout.getChildren().addAll(lblTitulo, lblInfo, btnGerarPDF, btnVoltar);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));

        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
    }
}
