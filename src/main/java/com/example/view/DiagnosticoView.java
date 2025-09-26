package com.example.view;

import com.example.controller.DiagnosticoController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class DiagnosticoView extends Application {

    private DiagnosticoController controller = new DiagnosticoController();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Tela de Diagnóstico");

        // Buscar versão do banco
        String versao = controller.obterVersaoBanco();

        // Montar textos
        String paragrafo1 = "Validado que o servidor do banco de dados é um Linux com o nome de ORCTEST "
                + "e está sob gerência da equipe da Cloud, o banco de dados Oracle é licenciado "
                + "sendo a versão: " + versao + ".";

        String paragrafo2 = "Informado pelo cliente que após a atualização do sistema da x.x.x.x.x para a x.x.x.x, "
                + "onde na sequência houve outra atualização para a x.x.x.x para resolução de outro problema "
                + "no sistema, a rotina DE_EMISS passou a apresentar travamentos ao gerar o relatório. "
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
        grid.setVgap(10);

        // Adicionar Labels
        grid.add(new Label("Diagnóstico:"), 0, 0);

        Label label1 = new Label(paragrafo1);
        label1.setWrapText(true);
        grid.add(label1, 0, 1);

        Label label2 = new Label(paragrafo2);
        label2.setWrapText(true);
        grid.add(label2, 0, 2);

        Label label3 = new Label(paragrafo3);
        label3.setWrapText(true);
        grid.add(label3, 0, 3);

        Scene scene = new Scene(grid, 800, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
