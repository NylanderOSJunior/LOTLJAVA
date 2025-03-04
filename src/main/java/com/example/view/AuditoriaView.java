package com.example.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

import com.example.controller.AuditoriaController;
import com.example.model.Auditoria;

public class AuditoriaView extends Application {
    private AuditoriaController auditoriaController = new AuditoriaController();
    private TableView<Auditoria> tableView = new TableView<>();
    private Button btnFechar = new Button("Fechar");

    @Override
    public void start(Stage stage) {
        // Definição das colunas da tabela
        TableColumn<Auditoria, String> colUsuario = new TableColumn<>("Usuário");
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));

        TableColumn<Auditoria, String> colAcao = new TableColumn<>("Ação");
        colAcao.setCellValueFactory(new PropertyValueFactory<>("acao"));

        TableColumn<Auditoria, String> colData = new TableColumn<>("Data");
        colData.setCellValueFactory(new PropertyValueFactory<>("data"));

        TableColumn<Auditoria, String> colObjeto = new TableColumn<>("Objeto");
        colObjeto.setCellValueFactory(new PropertyValueFactory<>("objeto"));

        // Adicionando colunas à tabela
        tableView.getColumns().addAll(colUsuario, colAcao, colData, colObjeto);
        tableView.setPlaceholder(new Label("Nenhuma auditoria encontrada."));

        // Botão para fechar a janela
        btnFechar.setOnAction(e -> stage.close());

        // Layout da tela
        VBox layout = new VBox(10, tableView, btnFechar);
        layout.setAlignment(Pos.CENTER);
        layout.setPrefSize(600, 400);

        // Configuração da cena e exibição da janela
        Scene scene = new Scene(layout);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Auditoria do Banco de Dados");
        stage.show();

        // Carregar dados na tabela
        carregarAuditoria();
    }

    private void carregarAuditoria() {
    // Chama o método do controlador que retorna uma lista simples (List)
    List<Auditoria> dadosAuditoria = auditoriaController.carregarAuditoria();

    // Converte a lista para ObservableList
    ObservableList<Auditoria> observableList = FXCollections.observableArrayList(dadosAuditoria);

    // Define os dados na tabela
    tableView.setItems(observableList);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
