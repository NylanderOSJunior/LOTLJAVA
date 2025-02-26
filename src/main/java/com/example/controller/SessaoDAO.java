package com.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.model.Sessao;

public class SessaoDAO {
    private Connection connection = ConexaoController.getConnection();

    // Método para buscar as sessões com filtro de usuário
    public ObservableList<Sessao> buscarSessoes(String filtro) {
        ObservableList<Sessao> sessoes = FXCollections.observableArrayList();
        String sql = "SELECT SID, SERIAL#, USERNAME, STATUS, MACHINE FROM V$SESSION";
        
        // Adiciona filtro de usuário, se fornecido
        if (filtro != null && !filtro.isEmpty()) {
            sql += " WHERE LOWER(USERNAME) LIKE LOWER(?)";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (filtro != null && !filtro.isEmpty()) {
                stmt.setString(1, "%" + filtro + "%");
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                sessoes.add(new Sessao(
                        rs.getInt("SID"),
                        rs.getInt("SERIAL#"),
                        rs.getString("USERNAME"),
                        rs.getString("STATUS"),
                        rs.getString("MACHINE")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sessoes;
    }

    // Método para obter a versão do banco de dados
    public String getVersaoBanco() {
        String versao = "Desconhecida";
        String sql = "SELECT * FROM V$VERSION WHERE BANNER LIKE 'Oracle%'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                versao = rs.getString("BANNER");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return versao;
    }
}
