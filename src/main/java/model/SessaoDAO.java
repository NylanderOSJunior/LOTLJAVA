package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SessaoDAO {
    private static final String URL = "jdbc:oracle:thin:@//SEU_HOST:1521/SEU_SERVICO";
    private static final String USER = "SEU_USUARIO";
    private static final String PASSWORD = "SUA_SENHA";

    public List<Sessao> listarSessoes(String filtroUsuario) {
        List<Sessao> sessoes = new ArrayList<>();
        String sql = "SELECT SID, SERIAL#, USERNAME, STATUS, MACHINE FROM V$SESSION WHERE USERNAME IS NOT NULL";
        
        if (filtroUsuario != null && !filtroUsuario.isEmpty()) {
            sql += " AND UPPER(USERNAME) LIKE ?";
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (filtroUsuario != null && !filtroUsuario.isEmpty()) {
                stmt.setString(1, "%" + filtroUsuario.toUpperCase() + "%");
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
}
