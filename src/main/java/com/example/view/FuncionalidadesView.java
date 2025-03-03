package com.example.view;

import com.example.controller.FuncionalidadesController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class FuncionalidadesView {
    private FuncionalidadesController controller = new FuncionalidadesController();

    public Region criarTela(StackPane contentArea) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Funcionalidades do Sistema");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Button btnAjusteFirewall = new Button("Ajustar Firewall");
        Button btnCriarBackup = new Button("Criar Backup");

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(btnAjusteFirewall, btnCriarBackup);

        // 🔥 Campos para Ajuste de Firewall
        VBox ajusteFirewallBox = new VBox(5);
        Label lblNomePorta = new Label("Nome da Porta:");
        TextField txtNomePorta = new TextField();
        Label lblPorta = new Label("Porta para liberar:");
        TextField txtPorta = new TextField();
        Button btnConfirmarFirewall = new Button("Liberar Porta");

        ajusteFirewallBox.getChildren().addAll(lblNomePorta, txtNomePorta, lblPorta, txtPorta, btnConfirmarFirewall);

        // 🔥 Campos para Criar Backup
        VBox criarBackupBox = new VBox(5);
        Label lblSchema = new Label("Schema:");
        TextField txtSchema = new TextField();
        Label lblService = new Label("Service:");
        TextField txtService = new TextField();
        Label lblDiretorio = new Label("Diretório Oracle:");
        TextField txtDiretorio = new TextField();
        Button btnSelecionarDiretorio = new Button("Selecionar Diretório");

        Label lblDiretoriobk = new Label("Diretório Backup:");
        TextField txtDiretoriobk = new TextField();
        Button btnSelecionarDiretorioBackup = new Button("Selecionar Diretório");

        Button btnConfirmarBackup = new Button("Criar Backup");

        criarBackupBox.getChildren().addAll(lblSchema, txtSchema, lblService, txtService, lblDiretorio, txtDiretorio, btnSelecionarDiretorio, lblDiretoriobk, txtDiretoriobk, btnSelecionarDiretorioBackup, btnConfirmarBackup);

        layout.getChildren().addAll(lblTitulo, buttonsBox);

        // 🎯 Vinculando ações dos botões
        btnAjusteFirewall.setOnAction(e -> {
            if (!layout.getChildren().contains(ajusteFirewallBox)) {
                layout.getChildren().add(ajusteFirewallBox);
            }
            layout.getChildren().remove(criarBackupBox);
        });

        btnCriarBackup.setOnAction(e -> {
            if (!layout.getChildren().contains(criarBackupBox)) {
                layout.getChildren().add(criarBackupBox);
            }
            layout.getChildren().remove(ajusteFirewallBox);
        });

        btnConfirmarFirewall.setOnAction(e -> controller.ajustarFirewall(txtNomePorta, txtPorta));
        btnConfirmarBackup.setOnAction(e -> controller.criarBackup(txtSchema, txtService, txtDiretoriobk, txtDiretorio));

        // 🎯 Ação para selecionar diretórios
        btnSelecionarDiretorio.setOnAction(e -> controller.selecionarDiretorio(txtDiretorio));
        btnSelecionarDiretorioBackup.setOnAction(e -> controller.selecionarDiretorio(txtDiretoriobk));

        // Botão para voltar à Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll(new Label("Bem-vindo à Home!")));

        layout.getChildren().add(btnVoltar);

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
