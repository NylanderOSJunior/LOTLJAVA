package com.example.model;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class SistemaInfoModel {
    
    // Usando a interface padrão do JDK para obter as informações do sistema
    private static final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

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

    // Método para obter o número de processadores
    public static int getNumProcessadores() {
        return osBean.getAvailableProcessors();
    }

    // Método para obter a carga média do sistema
    public static double getCargaMedia() {
        return osBean.getSystemLoadAverage();
    }

}
