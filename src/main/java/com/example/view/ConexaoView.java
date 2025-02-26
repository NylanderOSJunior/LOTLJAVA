package com.example.view;

import com.example.controller.ConexaoController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConexaoView extends Application {
    private final ConexaoController controller = new ConexaoController();

    @Override
    public void start(Stage primaryStage) {
        // Campos de entrada
        TextField hostField = criarTextField("Host", "localhost");
        TextField portaField = criarTextField("Porta", "1521");
        TextField servicoField = criarTextField("Serviço/SID", "XE");
        TextField usuarioField = criarTextField("Usuário", "");
        PasswordField senhaField = new PasswordField();
        senhaField.setPromptText("Senha");

        // Botões
        Button conectarButton = criarBotao("Conectar");
        Button btnVoltar = criarBotao("Voltar para Home");

        // Status da conexão
        Label statusLabel = new Label();
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");

        // Evento de clique no botão Conectar
        conectarButton.setOnAction(event -> {
            String host = hostField.getText();
            String porta = portaField.getText();
            String servico = servicoField.getText();
            String usuario = usuarioField.getText();
            String senha = senhaField.getText();

            if (controller.conectarOracle(host, porta, servico, usuario, senha)) {
                statusLabel.setText("✅ Conexão bem-sucedida!");
                abrirSessaoView(primaryStage);
            } else {
                statusLabel.setText("❌ Erro ao conectar.");
            }
        });

        // Evento do botão Voltar
        btnVoltar.setOnAction(e -> voltarParaHome(primaryStage));

        // Layout em Grid para os campos
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20));
        gridPane.setAlignment(Pos.CENTER);

        gridPane.addRow(0, new Label("Host:"), hostField);
        gridPane.addRow(1, new Label("Porta:"), portaField);
        gridPane.addRow(2, new Label("Serviço/SID:"), servicoField);
        gridPane.addRow(3, new Label("Usuário:"), usuarioField);
        gridPane.addRow(4, new Label("Senha:"), senhaField);

        // Layout principal
        VBox layout = new VBox(15, gridPane, conectarButton, btnVoltar, statusLabel);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // Configuração da pagina
        Scene scene = new Scene(layout, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Conexão com Banco Oracle");
        primaryStage.show();
    }

    // Método para criar TextField com placeholder e valor padrão
    private TextField criarTextField(String placeholder, String valorPadrao) {
        TextField textField = new TextField(valorPadrao);
        textField.setPromptText(placeholder);
        textField.setPrefWidth(200);
        return textField;
    }

    // Método para criar botões padronizados
    private Button criarBotao(String texto) {
        Button button = new Button(texto);
        button.getStyleClass().add("button");
        button.setPrefWidth(200);
        return button;
    }

    private void abrirSessaoView(Stage primaryStage) {
        try {
            new SessaoView().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void voltarParaHome(Stage primaryStage) {
        try {
            new HomeView().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
