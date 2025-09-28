package com.example.controller;

import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.stage.Window;

public class DiagnosticoController {

    private SessaoDAO sessao = new SessaoDAO();

    // Método para obter a versão do banco de dados
    public String obterVersaoBanco() {
        return sessao.getVersaoBanco();
    }

    // Método para imprimir qualquer Node (ex: GridPane)
    public void imprimir(Node node, Window window) {
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null && job.showPrintDialog(window)) {
            boolean success = job.printPage(node);
            if (success) {
                job.endJob();
                System.out.println("Documento enviado para impressão!");
            }
        }
    }
}
