package com.example.model;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class SistemaInfoModel {
    
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

    public static int getNumProcessadores() {
        return osBean.getAvailableProcessors();
    }

    public static double getCargaMedia() {
        return osBean.getSystemLoadAverage();
    }



    public static long getMemoriaTotal() {
        // Memória total do sistema
        return Runtime.getRuntime().totalMemory();
    }

    public static long getMemoriaLivre() {
        // Memória livre do sistema
        return Runtime.getRuntime().freeMemory();
    }

    public static String getEspacoEmDisco() {
        // Espaço livre no disco
        File file = new File("/");
        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        return "Total: " + totalSpace / (1024 * 1024 * 1024) + " GB, Livre: " + freeSpace / (1024 * 1024 * 1024) + " GB";
    }

    public static String getNomeHost() {
        // Nome do Host
        return System.getenv("COMPUTERNAME");
    }

    public static String getArquiteturaProcessador() {
        // Arquitetura do processador (32-bit ou 64-bit)
        return System.getProperty("os.arch");
    }

    public static String getVersaoJava() {
        // Versão do Java
        return System.getProperty("java.version");
    }
}
