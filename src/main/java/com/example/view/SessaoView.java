package com.example.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.example.controller.ConexaoController;
import com.example.controller.SessaoController;
import com.example.model.Sessao;

public class SessaoView extends Application {
    private ConexaoController controllercon = new ConexaoController();
    private SessaoController controller = new SessaoController();
    private TableView<Sessao> tableView = new TableView<>();
    private TableView<Sessao> tableBlock = new TableView<>();
    private TextField searchField = new TextField();
    private PieChart pieChart = new PieChart();
    private Button exportButton = new Button("Exportar CSV");
    private Button btnVoltarcon = new Button("Voltar");
    private Button btnVoltar = new Button("Voltar para Home");
    private Button btnAplicarTempo = new Button("Aplicar Tempo");
    private Button btnAuditoria = new Button("Auditoria Oracle");
    private Label tempoAtividadeLabel;

    // Spinner e botão para ajustar o intervalo de recarregamento
    private Spinner<Integer> spinnerTempo = new Spinner<>(5, 60, 5); // Intervalo entre 5 e 60 segundos
    private Timeline timeline;

    @SuppressWarnings("unchecked")
    @Override
public void start(Stage primaryStage) {

    // Títulos das tabelas
    tableView.setPlaceholder(new Label("Monitoramento de Sessões"));
    tableBlock.setPlaceholder(new Label("Sem Sessões em Lock"));

    TableColumn<Sessao, Integer> colSid = new TableColumn<>("SID");
    colSid.setCellValueFactory(new PropertyValueFactory<>("sid"));

    TableColumn<Sessao, Integer> colSql_id = new TableColumn<>("SQL_ID");
    colSql_id.setCellValueFactory(new PropertyValueFactory<>("sql_id"));

    TableColumn<Sessao, String> colUsername = new TableColumn<>("Schema");
    colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Sessao, String> colOsuser = new TableColumn<>("Usuário");
    colOsuser.setCellValueFactory(new PropertyValueFactory<>("osuser"));

    TableColumn<Sessao, String> colProgram = new TableColumn<>("Modulo");
    colProgram.setCellValueFactory(new PropertyValueFactory<>("program"));

    TableColumn<Sessao, String> colStatus = new TableColumn<>("Status");
    colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    TableColumn<Sessao, String> colMachine = new TableColumn<>("Máquina");
    colMachine.setCellValueFactory(new PropertyValueFactory<>("machine"));

    TableColumn<Sessao, String> colSeconds_in_wait = new TableColumn<>("TempoExec");
    colSeconds_in_wait.setCellValueFactory(new PropertyValueFactory<>("seconds_in_wait"));

    TableColumn<Sessao, String> colSql_text = new TableColumn<>("Comando SQL");
    colSql_text.setCellValueFactory(new PropertyValueFactory<>("sql_text"));

    TableColumn<Sessao, Integer> colBSid = new TableColumn<>("SID");
    colBSid.setCellValueFactory(new PropertyValueFactory<>("sid"));

    TableColumn<Sessao, Integer> colBSql_id = new TableColumn<>("SQL_ID");
    colBSql_id.setCellValueFactory(new PropertyValueFactory<>("sql_id"));

    TableColumn<Sessao, String> colBUsername = new TableColumn<>("Schema");
    colBUsername.setCellValueFactory(new PropertyValueFactory<>("username"));

    TableColumn<Sessao, String> colBOsuser = new TableColumn<>("Usuário");
    colBOsuser.setCellValueFactory(new PropertyValueFactory<>("osuser"));

    TableColumn<Sessao, String> colBProgram = new TableColumn<>("Modulo");
    colBProgram.setCellValueFactory(new PropertyValueFactory<>("program"));

    TableColumn<Sessao, String> colBStatus = new TableColumn<>("Status");
    colBStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

    TableColumn<Sessao, String> colEvent = new TableColumn<>("Evento");
    colEvent.setCellValueFactory(new PropertyValueFactory<>("event"));

    TableColumn<Sessao, String> colBlocking_session = new TableColumn<>("SessaoDoLock");
    colBlocking_session.setCellValueFactory(new PropertyValueFactory<>("blocking_session"));

    tableView.getColumns().addAll(colSid, colSql_id, colUsername, colOsuser, colProgram, colStatus, colMachine, colSeconds_in_wait, colSql_text);
    tableBlock.getColumns().addAll(colBSid, colBSql_id, colBUsername, colBOsuser, colBProgram, colBStatus, colEvent, colBlocking_session);

     // Labels para os títulos
     Label labelMonitoramento = new Label("Monitoramento de Sessões");
     labelMonitoramento.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
 
     Label labelLock = new Label("Sessões em Lock");
     labelLock.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

    // Spinner para selecionar o tempo de recarregamento (em segundos)
    spinnerTempo.setEditable(true); // Permite edição manual do tempo
    spinnerTempo.setMaxWidth(60);

    // Criando o timeline inicial com o intervalo padrão de 5 segundos
    timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> atualizarTabela()));
    timeline.setCycleCount(Timeline.INDEFINITE);  // Recarregar indefinidamente
    timeline.play();  // Inicia o timeline

    // Aplicar o novo tempo de recarregamento
    btnAplicarTempo.setOnAction(e -> aplicarTempoRecarregamento());

    // Listener para seleção de linha e exibição do comando SQL
    tableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            String sqlText = newValue.getSql_text(); // Obtém o comando SQL
            abrirTelaComandoSQL(sqlText);  // Abre a tela com o comando SQL
        }
    });

    // Listener para fechar o aplicativo e desconectar do banco de dados
    primaryStage.setOnCloseRequest(event -> {
        controllercon.desconectar(); // Chama o método de desconexão
        System.out.println("Aplicativo fechado e sessão do Oracle encerrada.");
    });

    // Label para exibir a versão do banco
    Label versaoLabel = new Label("Versão do Banco: Carregando...");
    // Chamar o método para obter a versão do banco e exibir
    String versaoBanco = controller.obterVersaoBanco();
    versaoLabel.setText("Versão do Banco: " + versaoBanco);

    // Label para exibir a CPUs do banco
    Label cpuLabel = new Label("Quantidade de CPUS Alocadas ao Banco: Carregando...");
    // Chamar o método para obter as CPUs do banco e exibir
    int cpuBanco = controller.obterCPUBanco();
    cpuLabel.setText("CPU Alocadas ao Banco: " + cpuBanco);

    // Label para exibir a CPUs do banco
    Label consumoLabel = new Label("Procentagem de CPU consumida no Banco: Carregando...");
    // Chamar o método para obter as CPUs do banco e exibir
    String consumoBanco = controller.obterConsumoBanco();
    consumoLabel.setText("Consumo de CPU no banco de dados: " + consumoBanco+"%");

    // Label para exibir estatitiscas da tabela
    Label estatisticatabLabel = new Label("Carregando data de estatística de tabelas de banco: Carregando...");
    // Chamar o método para obter data de estetistica de tabela mais antiga
    String estatisticatabBanco = controller.MinLastAnalyzedTables();
    estatisticatabLabel.setText("Data da estatística de tabelas mais destualizada ativa: " + estatisticatabBanco);

    // Label para exibir estatitiscas de indices
    Label estatisticaindLabel = new Label("Carregando data de estatística de indices de banco: Carregando...");
    // Chamar o método para obter data de estetistica de tabela mais antiga
    String estatisticaindBanco = controller.MinLastAnalyzedIndexes();
    estatisticaindLabel.setText("Data da estatística de indices mais destualizada ativa: " + estatisticaindBanco);

    // Label para exibir o tempo de atividade do banco
    Label tempoAtividadeLabel = new Label("Tempo de Atividade do Banco: Carregando...");

    // Método para obter e atualizar o tempo de atividade
    atualizarTempoAtividade(tempoAtividadeLabel);
    

    // Botão para chamar tela auditoria
    btnAuditoria.setOnAction(e -> abrirTelaAuditoria());

    // Campo de busca
    searchField.setPromptText("Filtrar por usuário...");
    searchField.setOnKeyReleased(event -> atualizarTabela());

    // Botão de exportação
    exportButton.setOnAction(e -> exportarParaCSV());


    //botão de voltar para a Home
    btnVoltar.setOnAction(e -> voltarParaHome(primaryStage));

    //botão de voltar para a Tela conexões
    btnVoltarcon.setOnAction(e -> voltarParaCon(primaryStage));

    HBox searchBox = new HBox(10, searchField, spinnerTempo, btnAplicarTempo, btnAuditoria);
    searchBox.setAlignment(Pos.CENTER_LEFT);
    
    HBox searchBox2 = new HBox(10, exportButton, btnVoltarcon, btnVoltar);
    searchBox2.setAlignment(Pos.CENTER_LEFT);

    HBox searchBox3 = new HBox(10, labelMonitoramento, tempoAtividadeLabel);
    searchBox3.setAlignment(Pos.CENTER_LEFT);

    // Melhorias na interface
    VBox vbox = new VBox(searchBox, searchBox3, tableView, labelLock, tableBlock, pieChart, versaoLabel, estatisticatabLabel, estatisticaindLabel, cpuLabel, consumoLabel, searchBox2); // Adiciona o Label de versão
    vbox.setSpacing(10);
    vbox.getStyleClass().add("main-container");

    Scene scene = new Scene(vbox, 850, 650);
    scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
    primaryStage.setScene(scene);
    primaryStage.setTitle("Monitoramento de Sessões");
    primaryStage.show();

    atualizarTabela();
}

private void abrirTelaAuditoria() {
    Stage stage = new Stage();
    new AuditoriaView().start(stage);
}


    private void aplicarTempoRecarregamento() {
        // Pega o tempo selecionado no spinner
        int tempoSelecionado = spinnerTempo.getValue();
        
        // Para o timeline atual (se já houver um)
        if (timeline != null) {
            timeline.stop();  // Para o timeline atual
        }

        // Cria um novo Timeline com o intervalo ajustado
        timeline = new Timeline(new KeyFrame(Duration.seconds(tempoSelecionado), e -> atualizarTabela()));
        timeline.setCycleCount(Timeline.INDEFINITE);  // Recarregar indefinidamente
        timeline.play();  // Inicia o novo timeline
    }

    private void atualizarTempoAtividade(Label tempoAtividadeLabel) {
        new Thread(() -> {
            ObservableList<Sessao> dadosBanco = controller.tempoExecucaoBanco();
            
            if (!dadosBanco.isEmpty()) {
                Sessao sessao = dadosBanco.get(0);
                String startupTime = sessao.getStartup_time();
                String hoursUp = sessao.getHours_up();
                String daysUp = sessao.getDays_up();

                javafx.application.Platform.runLater(() -> {
                    tempoAtividadeLabel.setText("Banco Ativo desde: " + startupTime + 
                        " | Horas Ativo: " + hoursUp + "h" + 
                        " | Dias Ativo: " + daysUp + "d");
                });
            }
        }).start();
    }


    private void atualizarTabela() {
        new Thread(() -> {
            ObservableList<Sessao> sessoes = controller.carregarSessoes(searchField.getText());
            ObservableList<Sessao> sessoesblock = controller.carregarSessoesblock(searchField.getText());
    
            javafx.application.Platform.runLater(() -> {
                tableView.getItems().clear();
                tableBlock.getItems().clear();
                tableView.setItems(FXCollections.observableArrayList(sessoes));
                tableBlock.setItems(FXCollections.observableArrayList(sessoesblock));
                atualizarGrafico();
                verificarConsumoAlto(sessoes);
    
                // Atualiza o tempo de atividade do banco
                atualizarTempoAtividade(tempoAtividadeLabel);
            });
        }).start();
    }
    
    private void abrirTelaComandoSQL(String sqlText) {
        ComandoSQLView comandoSQLView = new ComandoSQLView(sqlText);
        Stage stage = new Stage();
        comandoSQLView.start(stage);
    }

    private void voltarParaHome(Stage primaryStage) {
        //Desconectar sessão
        controllercon.desconectar();
        // Carregar a tela home
        new HomeView().start(primaryStage);
    }

    private void voltarParaCon(Stage primaryStage) {
        //Desconectar sessão
        controllercon.desconectar();
        // Carregar a tela home
        new ConexaoView().start(primaryStage);
    }

    
    

    private void atualizarGrafico() {
        int ativas = 0, inativas = 0;
    
        // Conta as sessões ativas e inativas
        for (Sessao sessao : tableView.getItems()) {
            if ("ACTIVE".equalsIgnoreCase(sessao.getStatus())) {
                ativas++;
            } else {
                inativas++;
            }
        }
    
        // Cria os dados do gráfico
        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                new PieChart.Data("Ativas (" + ativas + ")", ativas),
                new PieChart.Data("Inativas (" + inativas + ")", inativas)
        );
    
        // Atualiza o gráfico com as novas informações
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
                writer.append("SID,SQL_ID,Schema,Usuário,Modulo,Status,Máquina,DuracaoConsult,Comando\n");
                for (Sessao sessao : tableView.getItems()) {
                    writer.append(sessao.getSid() + "," + sessao.getSql_id() + ","
                            + sessao.getUsername() + "," + sessao.getOsuser() + "," 
                            + sessao.getProgram() + "," + sessao.getStatus() + ","
                            + sessao.getMachine() + "," + sessao.getSeconds_in_wait() + ","
                            + sessao.getSql_text() + "\n");
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
