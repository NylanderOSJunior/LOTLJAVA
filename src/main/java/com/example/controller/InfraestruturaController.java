<<<<<<< HEAD
package com.example.controller;

import com.example.model.SistemaInfoModel;

public class InfraestruturaController {

    
    public String obterInformacoesSistema() {
        return "Sistema Operacional: " + SistemaInfoModel.getSistemaOperacional() + "\n" +
               "Versão: " + SistemaInfoModel.getVersao() + "\n" +
               "Arquitetura: " + SistemaInfoModel.getArquitetura() + "\n" +
               "Usuário: " + SistemaInfoModel.getUsuario();
    }
}
=======
package com.example.controller;

import com.example.model.SistemaInfoModel;

public class InfraestruturaController {

    
    public String obterInformacoesSistema() {
        return "Sistema Operacional: " + SistemaInfoModel.getSistemaOperacional() + "\n" +
               "Versão: " + SistemaInfoModel.getVersao() + "\n" +
               "Arquitetura: " + SistemaInfoModel.getArquitetura() + "\n" +
               "Usuário: " + SistemaInfoModel.getUsuario();
    }
}
>>>>>>> 6f16e8280fb6c55f7700b702cefa24b11130a45c
