package com.example.controller;

import com.example.model.Sessao;

import javafx.collections.ObservableList;

public class SessaoController {
    private SessaoDAO sessaoDAO = new SessaoDAO();

    // Método para carregar sessões com base no filtro
    public ObservableList<Sessao> carregarSessoes(String filtro) {
        return sessaoDAO.buscarSessoes(filtro);
    }

    // Método para obter a versão do banco de dados
    public String obterVersaoBanco() {
        return sessaoDAO.getVersaoBanco();
    }
}
