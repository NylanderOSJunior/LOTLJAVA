package com.example.controller;

import com.example.model.Sessao;

import javafx.collections.ObservableList;

public class SessaoController {
    private SessaoDAO sessaoDAO = new SessaoDAO();

    // Método para carregar sessões com base no filtro
    public ObservableList<Sessao> carregarSessoes(String filtro) {
        return sessaoDAO.buscarSessoes(filtro);
    }
    

    // Método para obter as sessões com lock no banco de dados
    public ObservableList<Sessao> carregarSessoesblock(String filtro) {
        return sessaoDAO.buscarSessoesBloqueadas();
    }

    // Método para obter o tempo de atividade do banco de dados
    public ObservableList<Sessao> tempoExecucaoBanco() {
        return sessaoDAO.execucaoBanco();
    }

    // Método para obter a versão do banco de dados
    public String obterVersaoBanco() {
        return sessaoDAO.getVersaoBanco();
    }

    // Método para obter o consumo de CPU do banco de dados
    public String obterConsumoBanco() {
        return sessaoDAO.getConsumoCPU();
    }

    // Método para obter a quantidade de CPUs configuradas para o banco de dados
    public int obterCPUBanco() {
        return sessaoDAO.getCPUBanco();
    }

    // Método para obter a menor data de análise das tabelas do schema conectado
    public String MinLastAnalyzedTables() {
        return sessaoDAO.getMinLastAnalyzedTables();
    }

    // Método para obter a menor data de análise dos índices do schema conectado
    public String MinLastAnalyzedIndexes() {
        return sessaoDAO.getMinLastAnalyzedIndexes();
    }

    // Método para encerrar a sessão
    public boolean encerrarSessao(int sid, int serial) {
        // Chama o método no DAO para encerrar a sessão com base no SID e SERIAL
        return sessaoDAO.encerrarSessao(sid, serial);
    }
    
}
