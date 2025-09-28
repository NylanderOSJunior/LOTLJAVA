package com.example.view;

import com.example.controller.DiagnosticoController;
import com.example.controller.SessaoController;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DiagnosticoView extends Application {

    private DiagnosticoController controller = new DiagnosticoController();
    private SessaoController control = new SessaoController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tela de Diagnóstico");

        // Buscar versão do banco
        String versao = control.obterVersaoBanco();

        // Textos
        String paragrafo1 = "Validado que o servidor do banco de dados é um Linux com o nome de ORCXXX "
                + "e está sob gerência da equipe da Cloud, o banco de dados Oracle é licenciado "
                + "sendo a versão: " + versao + ".";

        String paragrafo2 = "Informado pelo cliente que após a atualização do sistema da XXXXXX para a XXXXX, "
                + "onde na sequência houve outra atualização para a XXXXX para resolução de outro problema "
                + "no sistema, a rotina XXXXXX passou a apresentar travamentos ao gerar o relatório. "
                + "Informado também que sempre utilizou os filtros repassados onde somente no filtro de grupo "
                + "eram retiradas as opções de serviços, filtro período de recebimento leva o tempo de um mês "
                + "ou seja 01/08/2025 a 31/08/2025 os demais filtros levavam todos os dados.";

        String paragrafo3 = "Após a passagem de todos os filtros de grupo exceto os grupos de serviço, "
                + "a cliente clica em “Avançar” e em seguida o sistema começa a geração do relatório, porém "
                + "ele demora sair da tela de carregamento, podendo levar até 30 minutos, o que é bastante tempo, "
                + "pois ele leva em torno de 10 minutos muito para ser gerado.";

        // Layout do formulário
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setHgap(10);
        grid.setVgap(15);

        // ===== TÍTULO PRINCIPAL =====
        Label titulo = new Label("Relatório de Diagnóstico");
        titulo.getStyleClass().add("titulo"); // estilo CSS aplicado
        grid.add(titulo, 0, 0);

        // ===== SEÇÃO 1 =====
        VBox secao1 = new VBox(5);
        Label subtitulo1 = new Label("1. Ambiente de Banco de Dados");
        subtitulo1.getStyleClass().add("subtitulo");
        Label texto1 = new Label(paragrafo1);
        texto1.getStyleClass().add("paragrafo");
        texto1.setWrapText(true);
        secao1.getChildren().addAll(subtitulo1, texto1);

        // ===== SEÇÃO 2 =====
        VBox secao2 = new VBox(5);
        Label subtitulo2 = new Label("2. Relato do Cliente");
        subtitulo2.getStyleClass().add("subtitulo");
        Label texto2 = new Label(paragrafo2);
        texto2.getStyleClass().add("paragrafo");
        texto2.setWrapText(true);
        secao2.getChildren().addAll(subtitulo2, texto2);

        // ===== SEÇÃO 3 =====
        VBox secao3 = new VBox(5);
        Label subtitulo3 = new Label("3. Observações sobre Performance");
        subtitulo3.getStyleClass().add("subtitulo");
        Label texto3 = new Label(paragrafo3);
        texto3.getStyleClass().add("paragrafo");
        texto3.setWrapText(true);
        secao3.getChildren().addAll(subtitulo3, texto3);

        // Botão imprimir
        Button btnImprimir = new Button("Imprimir Diagnóstico");
        btnImprimir.setOnAction(e -> controller.imprimir(grid, primaryStage.getOwner()));

        // Adicionar tudo ao Grid
        grid.add(secao1, 0, 1);
        grid.add(secao2, 0, 2);
        grid.add(secao3, 0, 3);
        grid.add(btnImprimir, 0, 4);

        // Cena
        Scene scene = new Scene(grid, 800, 600);

        // Estilos CSS
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
