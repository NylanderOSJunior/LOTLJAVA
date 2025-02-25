package view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class FuncionalidadesView extends Application {
    @Override
    public void start(Stage primaryStage) {

        // Criar botão de voltar para a Home
        Button btnVoltar = new Button("Voltar para Home");
        btnVoltar.setOnAction(e -> voltarParaHome(primaryStage));
        StackPane layout = new StackPane(new Label("Funcionalidades do Sistema"),btnVoltar);

        // Posiciona o botão no canto inferior direito
        StackPane.setAlignment(btnVoltar, Pos.TOP_RIGHT);
        Scene scene = new Scene(layout, 800, 500);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Funcionalidades");
        primaryStage.show();
    }

    private void voltarParaHome(Stage primaryStage) {
        // Carregar a tela home
        new HomeView().start(primaryStage);
    }
}
