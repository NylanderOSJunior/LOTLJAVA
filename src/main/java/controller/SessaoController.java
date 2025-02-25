package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Sessao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SessaoController {
    private Connection connection = ConexaoController.getConnection();

    public ObservableList<Sessao> carregarSessoes(String filtro) {
        ObservableList<Sessao> sessoes = FXCollections.observableArrayList();
        String sql = "SELECT SID, SERIAL#, USERNAME, STATUS, MACHINE FROM V$SESSION";
    
        // Aplica o filtro, se houver
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

    public String obterVersaoBanco() {
        String versao = "Versão não encontrada";
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
