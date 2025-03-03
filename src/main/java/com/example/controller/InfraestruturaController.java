package com.example.controller;

import com.example.model.SistemaInfoModel;

public class InfraestruturaController {

    public String obterInformacoesSistema() {
        return "Sistema Operacional: " + SistemaInfoModel.getSistemaOperacional() + "\n" +
               "Versão: " + SistemaInfoModel.getVersao() + "\n" +
               "Arquitetura: " + SistemaInfoModel.getArquitetura() + "\n" +
               "Usuário: " + SistemaInfoModel.getUsuario() + "\n" +
               "Número de Processadores: " + SistemaInfoModel.getNumProcessadores() + "\n" +
               "Carga Média do Sistema: " + SistemaInfoModel.getCargaMedia() + "\n" +
               "Memória Total: " + SistemaInfoModel.getMemoriaTotal() / (1024 * 1024) + " MB\n" +
               "Memória Livre: " + SistemaInfoModel.getMemoriaLivre() / (1024 * 1024) + " MB\n" +
               "Espaço em Disco: " + SistemaInfoModel.getEspacoEmDisco() + "\n" +
               "Nome do Host: " + SistemaInfoModel.getNomeHost() + "\n" +
               "Arquitetura do Processador: " + SistemaInfoModel.getArquiteturaProcessador() + "\n" +
               "Versão do Java: " + SistemaInfoModel.getVersaoJava();
    }
}
