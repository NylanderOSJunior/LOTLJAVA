<<<<<<< HEAD
package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoController {
    private static Connection connection;

    public boolean conectarOracle(String host, String porta, String servico, String usuario, String senha) {
        // Corrigir a URL para usar a forma //host:porta/servico
        String url = "jdbc:oracle:thin:@//" + host + ":" + porta + "/" + servico;

        try {
            // Tenta carregar o driver Oracle (caso necessário)
            Class.forName("oracle.jdbc.OracleDriver");

            // Conectar ao banco de dados
            connection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectado ao Oracle com sucesso!");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
=======
package com.example.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoController {
    private static Connection connection;

    public boolean conectarOracle(String host, String porta, String servico, String usuario, String senha) {
        // Corrigir a URL para usar a forma //host:porta/servico
        String url = "jdbc:oracle:thin:@//" + host + ":" + porta + "/" + servico;

        try {
            // Tenta carregar o driver Oracle (caso necessário)
            Class.forName("oracle.jdbc.OracleDriver");

            // Conectar ao banco de dados
            connection = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conectado ao Oracle com sucesso!");
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}
>>>>>>> 6f16e8280fb6c55f7700b702cefa24b11130a45c
