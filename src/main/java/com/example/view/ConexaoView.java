package com.example.view;

import com.example.controller.ConexaoController;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConexaoView extends Application {
    private ConexaoController controller = new ConexaoController();

    @Override
    public void start(Stage primaryStage) {
        TextField hostField = new TextField("localhost");
        TextField portaField = new TextField("1521");
        TextField servicoField = new TextField("XE"); // Nome do serviço ou SID
        TextField usuarioField = new TextField();
        PasswordField senhaField = new PasswordField();
        Button conectarButton = new Button("Conectar");


        hostField.setPromptText("Host");
        portaField.setPromptText("Porta");
        servicoField.setPromptText("Serviço/SID");
        usuarioField.setPromptText("Usuário");
        senhaField.setPromptText("Senha");

        Label statusLabel = new Label();

        conectarButton.setOnAction(event -> {
            String host = hostField.getText();
            String porta = portaField.getText();
            String servico = servicoField.getText();
            String usuario = usuarioField.getText();
            String senha = senhaField.getText();

            if (controller.conectarOracle(host, porta, servico, usuario, senha)) {
                statusLabel.setText("Conexão bem-sucedida!");
                abrirSessaoView(primaryStage);
            } else {
                statusLabel.setText("Erro ao conectar.");
            }
        });

        VBox layout = new VBox(15,hostField, portaField, servicoField, usuarioField, senhaField, conectarButton, statusLabel);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Conexão com Banco Oracle");
        primaryStage.show();
    }

    private void abrirSessaoView(Stage primaryStage) {
        SessaoView sessaoView = new SessaoView();
        try {
            sessaoView.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
