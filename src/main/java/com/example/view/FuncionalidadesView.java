package com.example.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.HBox;

public class FuncionalidadesView {

    public VBox criarTela(StackPane contentArea) {
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label lblTitulo = new Label("Funcionalidades do Sistema");
        lblTitulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Criando os botões
        Button btnAjusteFirewall = new Button("Ajustar Firewall");
        Button btnCriarBackup = new Button("Criar Backup");

        // Criando um HBox para colocar os botões lado a lado
        HBox buttonsBox = new HBox(10); // Espaço de 10px entre os botões
        buttonsBox.setAlignment(Pos.CENTER); // Centraliza os botões no HBox
        buttonsBox.getChildren().addAll(btnAjusteFirewall, btnCriarBackup);

        // Criando os campos de preenchimento para Ajuste de Firewall
        VBox ajusteFirewallBox = new VBox(5);
        Label lblPorta = new Label("Porta para liberar:");
        TextField txtPorta = new TextField();
        Button btnConfirmarFirewall = new Button("Liberar Porta");

        ajusteFirewallBox.getChildren().addAll(lblPorta, txtPorta, btnConfirmarFirewall);
        
        // Criando os campos de preenchimento para Criar Backup
        VBox criarBackupBox = new VBox(5);
        Label lblUsuario = new Label("Usuário:");
        TextField txtUsuario = new TextField();
        Label lblSenha = new Label("Senha:");
        TextField txtSenha = new TextField();
        Label lblDiretorio = new Label("Diretório:");
        TextField txtDiretorio = new TextField();
        Button btnConfirmarBackup = new Button("Criar Backup");

        criarBackupBox.getChildren().addAll(lblUsuario, txtUsuario, lblSenha, txtSenha, lblDiretorio, txtDiretorio, btnConfirmarBackup);

        // Adiciona os botões ao layout principal
        layout.getChildren().addAll(lblTitulo, buttonsBox);

        // Ações dos botões para adicionar os campos
        btnAjusteFirewall.setOnAction(e -> {
            // Adiciona os campos de ajuste de firewall se ainda não estiverem no layout
            if (!layout.getChildren().contains(ajusteFirewallBox)) {
                layout.getChildren().add(ajusteFirewallBox);
            }
            // Remove os campos de backup, se estiverem no layout
            layout.getChildren().remove(criarBackupBox);
        });

        btnCriarBackup.setOnAction(e -> {
            // Adiciona os campos de criar backup se ainda não estiverem no layout
            if (!layout.getChildren().contains(criarBackupBox)) {
                layout.getChildren().add(criarBackupBox);
            }
            // Remove os campos de ajuste de firewall, se estiverem no layout
            layout.getChildren().remove(ajusteFirewallBox);
        });

        // Botão para voltar à Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll(new Label("Bem-vindo à Home!")));

        // Adiciona o botão "Voltar" ao layout
        layout.getChildren().add(btnVoltar);

        // Criando o ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(layout);
        scrollPane.setFitToWidth(true); // Ajusta à largura da tela
        scrollPane.setPadding(new Insets(10));

        // Criando um VBox para centralizar a barra de rolagem na tela
        VBox mainContainer = new VBox(scrollPane);
        mainContainer.setAlignment(Pos.CENTER);
        mainContainer.setPadding(new Insets(10));

        return mainContainer;
    }
}
