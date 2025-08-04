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

        CheckBox chkGerarBackup = new CheckBox("Gerar Backup");
        CheckBox chkBaseImplantacao = new CheckBox("Base Implantação");
        CheckBox chkCriarNovaBase = new CheckBox("Criar Nova Base");
        
        HBox flagsBox = new HBox(20);
        flagsBox.setAlignment(Pos.CENTER);
        flagsBox.getChildren().addAll(chkGerarBackup, chkBaseImplantacao, chkCriarNovaBase);
        

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
        Button btnSelecDiretorio = new Button("Selecionar Diretório");
        Label lblDiretorio = new Label("Diretório Backup:");
        TextField txtDiretorio = new TextField();

        btnGerarBackupoBox.getChildren().addAll(lblSchema, txtSchema, lblService, txtService, lblNomePorta, txtNomePorta, lblHost, txtHost, lblDiretorio, txtDiretorio, btnSelecDiretorio);

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

        btnCriarBasezeroBox.getChildren().addAll(lblSchemaB, txtSchemaB, lblServiceB, txtServiceB, btnSelecionarDiretorio, lblDiretoriobk, txtDiretoriobk, btnSelecionarDiretorioBackup);


        Button btnExecutar = new Button("Iniciar Execução");

        btnExecutar.setOnAction(e -> {
            if (chkGerarBackup.isSelected()) {
                controller.GeraBackup(txtSchema, txtService, txtNomePorta, txtHost);
            }

            if (chkBaseImplantacao.isSelected()) {
                controller.criarBackup(txtSchemaB, txtServiceB, txtDiretoriobk);
            }

            if (chkCriarNovaBase.isSelected()) {
                // Adicione aqui a lógica quando você tiver os campos e método prontos
                // Ex: controller.criarNovaBase(...);
            }

            if (!chkGerarBackup.isSelected() && !chkBaseImplantacao.isSelected() && !chkCriarNovaBase.isSelected()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Selecione ao menos uma opção para executar.", ButtonType.OK);
                alert.showAndWait();
            }
        });

        // 🎯 Ação para selecionar diretórios
        btnSelecDiretorio.setOnAction(e -> controller.selecionarDiretorio(txtDiretorio));
        btnSelecionarDiretorio.setOnAction(e -> controller.selecionarDiretorio(txtDiretoriobk));
        btnSelecionarDiretorioBackup.setOnAction(e -> controller.selecionarDiretorio(txtDiretoriobk));

        // Botão para voltar à Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> contentArea.getChildren().setAll());

                //  Vinculando ações dos botões
        chkGerarBackup.setOnAction(e -> {
            if (chkGerarBackup.isSelected()) {
                if (!layout.getChildren().contains(btnGerarBackupoBox)) {
                    layout.getChildren().add(btnGerarBackupoBox);
                }
            } else {
                layout.getChildren().remove(btnGerarBackupoBox);
            }
        });
        
        chkBaseImplantacao.setOnAction(e -> {
            if (chkBaseImplantacao.isSelected()) {
                if (!layout.getChildren().contains(btnCriarBasezeroBox)) {
                    layout.getChildren().add(btnCriarBasezeroBox);
                }
            } else {
                layout.getChildren().remove(btnCriarBasezeroBox);
            }
        });
        
        // Se tiver VBox para "Criar Nova Base", adicione lógica semelhante:
        chkCriarNovaBase.setOnAction(e -> {
            // Exemplo: layout.getChildren().add(btnCriarBaseBox);
        });

        layout.getChildren().addAll(lblTitulo, flagsBox,btnExecutar,btnVoltar);
        

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
