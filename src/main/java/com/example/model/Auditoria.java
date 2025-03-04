package com.example.model;

public class Auditoria {
    private String usuario;
    private String acao;
    private String data;
    private String objeto;

    public Auditoria(String usuario, String acao, String data, String objeto) {
        this.usuario = usuario;
        this.acao = acao;
        this.data = data;
        this.objeto = objeto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }
}
