package com.example.view;

import com.example.controller.CriaBaseController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class CriaBaseView {
    private CriaBaseController controller = new CriaBaseController();

    public Region criarTela(StackPane contentArea) {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Funções Cria Base");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnGerarBackup = new Button("Gerar Backup");
        Button btnCriarBasezero = new Button("Base Implantação");
        Button btnCriarBase = new Button("Criar Nova Base");


        HBox buttonsBox = new HBox(20);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(btnGerarBackup, btnCriarBasezero, btnCriarBase);

        // 🔥 Campos para Gerar Backup
        VBox btnGerarBackupoBox = new VBox(10);
        Label lblSchema = new Label("Schema:");
        TextField txtSchema = new TextField();
        Label lblService = new Label("Service:");
        TextField txtService = new TextField();
        Label lblNomePorta = new Label("Porta:");
        TextField txtNomePorta = new TextField();
        Label lblHost = new Label("Host:");
        TextField txtHost = new TextField();
        Button btnGerarB = new Button("Gerar");

        btnGerarBackupoBox.getChildren().addAll(lblSchema, txtSchema, lblService, txtService, lblNomePorta, txtNomePorta, lblHost, txtHost, btnGerarB);

        // 🔥 Campos para Criar Base Zerada
        VBox btnCriarBasezeroBox = new VBox(10);
        Label lblSchemaB = new Label("Schema:");
        TextField txtSchemaB = new TextField();
        Label lblServiceB = new Label("Service:");
        TextField txtServiceB = new TextField();
        Label lblNomePortaB = new Label("Porta:");
        TextField txtNomePortaB = new TextField();
        Label lblHostB = new Label("Host:");
        TextField txtHostB = new TextField();
        Button btnSelecionarDiretorio = new Button("Selecionar Diretório");
        Label lblDiretoriobk = new Label("Diretório Backup:");
        TextField txtDiretoriobk = new TextField();
        Button btnSelecionarDiretorioBackup = new Button("Selecionar Diretório");

        Button btnConfirmarBackup = new Button("Criar Backup");

        btnCriarBasezeroBox.getChildren().addAll(lblSchemaB, txtSchemaB, lblServiceB, txtServiceB, btnSelecionarDiretorio, lblDiretoriobk, txtDiretoriobk, btnSelecionarDiretorioBackup, btnConfirmarBackup);

        layout.getChildren().addAll(lblTitulo, buttonsBox);

        btnGerarB.setOnAction(e -> controller.GeraBackup(txtSchema,txtService,txtNomePorta, txtHost));
        btnConfirmarBackup.setOnAction(e -> controller.criarBackup(txtSchema, txtService, txtDiretoriobk));

        // 🎯 Ação para selecionar diretórios
        btnSelecionarDiretorio.setOnAction(e -> controller.selecionarDiretorio(txtDiretoriobk));
        btnSelecionarDiretorioBackup.setOnAction(e -> controller.selecionarDiretorio(txtDiretoriobk));

        // Botão para voltar à Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll());

        layout.getChildren().add(btnVoltar);

        //  Vinculando ações dos botões
        btnGerarBackup.setOnAction(e -> {
            if (!layout.getChildren().contains(btnGerarBackupoBox)) {
                layout.getChildren().add(btnGerarBackupoBox);
            }
            layout.getChildren().remove(btnCriarBasezeroBox);
        });

        btnCriarBasezero.setOnAction(e -> {
            if (!layout.getChildren().contains(btnCriarBasezeroBox)) {
                layout.getChildren().add(btnCriarBasezeroBox);
            }
            layout.getChildren().remove(btnGerarBackupoBox);
        });

        // 🧭 Adicionando o ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true);  // Ajusta o conteúdo ao tamanho da largura
        scrollPane.setFitToHeight(true); // Ajusta o conteúdo ao tamanho da altura
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barra de rolagem vertical quando necessário
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Barra de rolagem horizontal quando necessário

        return scrollPane;
    }
}
