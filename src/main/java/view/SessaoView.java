package view;

import controller.SessaoController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;
import model.Sessao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SessaoView extends Application {
    private SessaoController controller = new SessaoController();
    private TableView<Sessao> tableView = new TableView<>();
    private TextField searchField = new TextField();
    private PieChart pieChart = new PieChart();
    private Button exportButton = new Button("Exportar CSV");

    @SuppressWarnings("unchecked")
    @Override
public void start(Stage primaryStage) {
    TableColumn<Sessao, Integer> colSid = new TableColumn<>("SID");
    colSid.setCellValueFactory(new PropertyValueFactory<>("sid"));

    TableColumn<Sessao, Integer> colSerial = new TableColumn<>("Serial");
    colSerial.setCellValueFactory(new PropertyValueFactory<>("serial"));

    TableColumn<Sessao, String> colUsername = new TableColumn<>("Usuário");
    colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Sessao, String> colStatus = new TableColumn<>("Status");
    colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    TableColumn<Sessao, String> colMachine = new TableColumn<>("Máquina");
    colMachine.setCellValueFactory(new PropertyValueFactory<>("machine"));

    tableView.getColumns().addAll(colSid, colSerial, colUsername, colStatus, colMachine);

    // Label para exibir a versão do banco
    Label versaoLabel = new Label("Versão do Banco: Carregando...");

    // Chamar o método para obter a versão do banco e exibir
    String versaoBanco = controller.obterVersaoBanco();
    versaoLabel.setText("Versão do Banco: " + versaoBanco);

    // Campo de busca
    searchField.setPromptText("Filtrar por usuário...");
    searchField.setOnKeyReleased(event -> atualizarTabela());

    // Botão de exportação
    exportButton.setOnAction(e -> exportarParaCSV());

    // Atualização automática
    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> atualizarTabela()));
    timeline.setCycleCount(Timeline.INDEFINITE);
    timeline.play();

    // Melhorias na interface
    VBox vbox = new VBox(searchField, tableView, pieChart, versaoLabel, exportButton); // Adiciona o Label de versão
    vbox.setSpacing(10);
    vbox.getStyleClass().add("main-container");

    Scene scene = new Scene(vbox, 800, 500);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.setTitle("Monitoramento de Sessões");
    primaryStage.show();

    atualizarTabela();
}


    private void atualizarTabela() {
        // Executa a consulta em um thread separado para evitar bloqueio da UI
        new Thread(() -> {
            ObservableList<Sessao> sessoes = controller.carregarSessoes(searchField.getText());
            // Atualiza a UI na thread de execução (JavaFX)
            javafx.application.Platform.runLater(() -> {
                tableView.setItems(sessoes);
                atualizarGrafico();
                verificarConsumoAlto(sessoes);
            });
        }).start();
    }
    

    private void atualizarGrafico() {
        int ativas = 0, inativas = 0;

        for (Sessao sessao : tableView.getItems()) {
            if ("ACTIVE".equalsIgnoreCase(sessao.getStatus())) {
                ativas++;
            } else {
                inativas++;
            }
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Ativas", ativas),
                new PieChart.Data("Inativas", inativas)
        );

        pieChart.setData(pieData);
    }

    private void verificarConsumoAlto(ObservableList<Sessao> sessoes) {
        for (Sessao sessao : sessoes) {
            if (sessao.getUsername().equalsIgnoreCase("DBA_USER")) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Alerta de Consumo Alto");
                alert.setHeaderText("Sessão com alto consumo detectada!");
                alert.setContentText("Usuário: " + sessao.getUsername() + " - SID: " + sessao.getSid());
                alert.show();
                break;
            }
        }
    }

    private void exportarParaCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        fileChooser.setInitialFileName("sessoes_oracle.csv");
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.append("SID,Serial,Usuário,Status,Máquina\n");
                for (Sessao sessao : tableView.getItems()) {
                    writer.append(sessao.getSid() + "," + sessao.getSerial() + ","
                            + sessao.getUsername() + "," + sessao.getStatus() + ","
                            + sessao.getMachine() + "\n");
                }
                writer.flush();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Exportação Completa");
                alert.setHeaderText(null);
                alert.setContentText("Arquivo salvo com sucesso!");
                alert.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
