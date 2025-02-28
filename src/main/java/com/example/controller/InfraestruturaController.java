package com.example.controller;

import com.example.model.SistemaInfoModel;

public class InfraestruturaController {

    public String obterInformacoesSistema() {
        return "Sistema Operacional: " + SistemaInfoModel.getSistemaOperacional() + "\n" +
               "Versão: " + SistemaInfoModel.getVersao() + "\n" +
               "Arquitetura: " + SistemaInfoModel.getArquitetura() + "\n" +
               "Usuário: " + SistemaInfoModel.getUsuario() + "\n" +
               "Número de Processadores: " + SistemaInfoModel.getNumProcessadores() + "\n" +
               "Carga Média do Sistema: " + SistemaInfoModel.getCargaMedia();
    }
}
