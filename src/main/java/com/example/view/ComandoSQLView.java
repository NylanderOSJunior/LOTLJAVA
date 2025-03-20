package com.example.view;

import com.example.controller.SessaoController;
import com.example.model.Sessao;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ComandoSQLView extends Application {
    private int sid;
    private int serial;
    private String sqlText;
    private Button btnClose = new Button("Fechar");
    private Button btnKilsession = new Button("Encerrar Sessão Lock");

    public ComandoSQLView(int sid, int serial, String sqlText) {
        this.sid = sid; // Recebe o SID a ser exibido
        this.serial = serial; // Recebe o Serial a ser exibido
        this.sqlText = sqlText; // Recebe o comando SQL a ser exibido
    }

    @Override
    public void start(Stage stage) {
        // Cria um TextArea para exibir o comando SQL
        TextArea textArea = new TextArea();
        textArea.setText(sqlText); // Define o texto do TextArea com o SQL recebido
        textArea.setWrapText(true); // Permite a quebra de linha no texto
        textArea.setEditable(false); // Não permite edição do texto

        // Exibir o SID
        Label labelSid = new Label("SID: " + sid);
        Label labelSerial = new Label("Serial: " + serial);

        // Ação do botão "Fechar" para fechar a janela
        btnClose.setOnAction(e -> stage.close());

        // Ação do botão "Encerrar Sessão Lock"
        btnKilsession.setOnAction(e -> {            
            // Chama o método de encerramento da sessão no controlador
            SessaoController controller = new SessaoController();
            boolean sucesso = controller.encerrarSessao(sid, serial);
            
            // Exibe um feedback para o usuário
            if (sucesso) {
                System.out.println("Sessão encerrada com sucesso.");
            } else {
                System.out.println("Erro ao encerrar a sessão.");
            }
        });

        // Layout para exibir o comando SQL e os botões
        VBox vbox = new VBox(labelSid, labelSerial, textArea, btnKilsession, btnClose);
        vbox.setSpacing(10);
        vbox.getStyleClass().add("main-container");

        // Cria a cena e exibe a janela
        Scene scene = new Scene(vbox, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Comando SQL Executado");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
