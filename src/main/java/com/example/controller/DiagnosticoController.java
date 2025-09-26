package com.example.controller;
import com.example.model.Sessao;
import javafx.collections.ObservableList;
public class DiagnosticoController {
    private SessaoDAO sessao = new SessaoDAO();

    // Método para obter a versão do banco de dados
    public String obterVersaoBanco() {
        return sessao.getVersaoBanco();
    }
    
}
