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

    // Método para obter o schema do banco conectado
    private String getCurrentSchema() {
        String schema = "";
        String sql = "SELECT SYS_CONTEXT('USERENV', 'CURRENT_SCHEMA') AS SCHEMA FROM DUAL";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                schema = rs.getString("SCHEMA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return schema;
    }

    // Método para obter a menor data de análise das tabelas do schema conectado
    public String getMinLastAnalyzedTables() {
        String schema = getCurrentSchema();
        String minLastAnalyzed = "Desconhecido";
        String sql = "SELECT MIN(last_analyzed) AS min_analyzed FROM dba_tables WHERE owner = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, schema);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                minLastAnalyzed = rs.getString("min_analyzed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minLastAnalyzed;
    }

    // Método para obter a menor data de análise dos índices do schema conectado
    public String getMinLastAnalyzedIndexes() {
        String schema = getCurrentSchema();
        String minLastAnalyzed = "Desconhecido";
        String sql = "SELECT MIN(last_analyzed) AS min_analyzed FROM dba_indexes WHERE owner = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, schema);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                minLastAnalyzed = rs.getString("min_analyzed");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return minLastAnalyzed;
    }


    // Método para buscar as sessões com filtro de usuário
    public ObservableList<Sessao> buscarSessoes(String filtro) {
        ObservableList<Sessao> sessoes = FXCollections.observableArrayList();
        String sql = "SELECT S.SID, NVL(S.SQL_ID, 'N/A') AS SQL_ID, S.USERNAME, S.OSUSER, S.PROGRAM, S.STATUS, S.MACHINE, " +
                     "CASE WHEN S.STATUS = 'INACTIVE' AND S.SQL_ID IS NULL THEN 0 ELSE S.SECONDS_IN_WAIT END AS SECONDS_IN_WAIT, " +
                     "Q.SQL_TEXT FROM V$SESSION S LEFT JOIN V$SQL Q ON S.SQL_ID = Q.SQL_ID WHERE S.USERNAME IS NOT NULL ORDER BY SECONDS_IN_WAIT DESC";
    
        if (filtro != null && !filtro.isEmpty()) {
            sql += " AND LOWER(S.OSUSER) LIKE LOWER(?)";
        }
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            if (filtro != null && !filtro.isEmpty()) {
                stmt.setString(1, "%" + filtro + "%");
            }
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Sessao sessao = new Sessao(
                    rs.getInt("SID"),
                    rs.getString("SQL_ID"),
                    rs.getString("USERNAME"),
                    rs.getString("OSUSER"),
                    rs.getString("PROGRAM"),
                    rs.getString("STATUS"),
                    rs.getString("MACHINE"),
                    rs.getInt("SECONDS_IN_WAIT"), // Tratado no SQL para ser 0 quando inativa e sem consulta
                    rs.getString("SQL_TEXT")
                );
                sessoes.add(sessao);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return sessoes;
    }
    

     // Método para buscar sessões bloqueadas
     public ObservableList<Sessao> buscarSessoesBloqueadas() {
        ObservableList<Sessao> sessoesBloqueadas = FXCollections.observableArrayList();
        String sql = "SELECT SID, SQL_ID, STATUS, USERNAME, OSUSER, PROGRAM, BLOCKING_SESSION, EVENT, SECONDS_IN_WAIT " +
                     "FROM GV$SESSION WHERE BLOCKING_SESSION IS NOT NULL";
    
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
    
            while (rs.next()) {
                Sessao sessao = new Sessao(
                    rs.getInt("SID"),
                    rs.getString("SQL_ID"),
                    rs.getString("USERNAME"),
                    rs.getString("OSUSER"),
                    rs.getString("PROGRAM"),
                    rs.getString("STATUS"),
                    rs.getString("BLOCKING_SESSION"),
                    rs.getString("EVENT"),
                    rs.getInt("SECONDS_IN_WAIT")
                );
                sessoesBloqueadas.add(sessao);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
    
        return sessoesBloqueadas;
    }

        // Método para buscar sessões bloqueadas
        public ObservableList<Sessao> execucaoBanco() {
            ObservableList<Sessao> execucaoBanco = FXCollections.observableArrayList();
            String sql = "SELECT TO_CHAR(STARTUP_TIME, 'DD/MM/YYYY HH24:MI:SS') AS STARTUP_TIME,"+
            " ROUND((SYSDATE - STARTUP_TIME) * 24, 2) AS HOURS_UP, ROUND(SYSDATE - STARTUP_TIME, 2) AS DAYS_UP FROM V$INSTANCE";
        
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
        
                while (rs.next()) {
                    Sessao sessao = new Sessao(
                        rs.getString("STARTUP_TIME"),
                        rs.getString("HOURS_UP"),
                        rs.getString("DAYS_UP")
                    );
                    execucaoBanco.add(sessao);
                }
        
            } catch (SQLException e) {
                e.printStackTrace();
            }
        
            return execucaoBanco;
        }

    /* Método para converter CLOB para String
    private String clobToString(Clob clob) {
        if (clob != null) {
            try {
                long length = clob.length();
                return clob.getSubString(1, (int) length);  // Converte o CLOB em String
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return "Desconhecido";
    }*/

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

        // Método para obter o consumo de CPU do banco de dados
        public String getConsumoCPU() {
            String consumo = "Desconhecida";
            String sql = "SELECT ROUND((CPU.VALUE / TIME.VALUE) * 100, 2) AS CPU_USAGE_PERCENTAGE "+
                            "FROM (SELECT VALUE FROM V$SYS_TIME_MODEL WHERE STAT_NAME = 'DB CPU') CPU,"+
                            "(SELECT VALUE FROM V$SYS_TIME_MODEL WHERE STAT_NAME = 'DB time') TIME";
            
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    consumo = rs.getString("CPU_USAGE_PERCENTAGE");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return consumo;
        }

    // Método para obter quantidade de CPUs alocadas ao banco de dados
    public int getCPUBanco() {
        int cpu=0;
        String sql = "SELECT VALUE FROM V$PARAMETER WHERE UPPER(NAME)= 'CPU_COUNT'";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                cpu = rs.getInt("VALUE");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cpu;
    }

    
}
/* Para Implementar
SELECT TABLESPACE_NAME, FILE_NAME, BYTES/1024/1024 AS TAMANHO_MB FROM DBA_DATA_FILES; --Consumo de tablespace
SELECT TABLESPACE_NAME, FREE_SPACE FROM DBA_FREE_SPACE; --Espaço livre 
SELECT USERNAME, ACCOUNT_STATUS, CREATED FROM DBA_USERS; -- User Criado e liberado.
SELECT OWNER,JOB_NAME, STATE, LAST_START_DATE, NEXT_RUN_DATE FROM DBA_SCHEDULER_JOBS; --Jobs Criadas
 */