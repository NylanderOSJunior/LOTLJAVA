package com.example.controller;

import com.example.model.Sessao;

import javafx.collections.ObservableList;

public class SessaoController {
    private SessaoDAO sessaoDAO = new SessaoDAO();

    // Método para carregar sessões com base no filtro
    public ObservableList<Sessao> carregarSessoes(String filtro) {
        return sessaoDAO.buscarSessoes(filtro);
    }

    public ObservableList<Sessao> carregarSessoesblock(String filtro) {
        return sessaoDAO.buscarSessoesBloqueadas();
    }

    // Método para obter a versão do banco de dados
    public String obterVersaoBanco() {
        return sessaoDAO.getVersaoBanco();
    }

    // Método para desconectar do Oracle
    public String Desonecao() {
        return sessaoDAO.getVersaoBanco();
    }
    
}
