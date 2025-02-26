package com.example.model;

public class SistemaInfoModel {
    public static String getSistemaOperacional() {
        return System.getProperty("os.name");
    }

    public static String getVersao() {
        return System.getProperty("os.version");
    }

    public static String getArquitetura() {
        return System.getProperty("os.arch");
    }

    public static String getUsuario() {
        return System.getProperty("user.name");
    }
}

