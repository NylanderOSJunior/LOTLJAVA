package com.example.controller;

import com.example.model.Auditoria;
import java.util.ArrayList;
import java.util.List;

public class AuditoriaDAO {

    public List<Auditoria> buscarAuditorias() {
        List<Auditoria> auditorias = new ArrayList<>();

        // Simulando dados do banco de dados
        auditorias.add(new Auditoria("user1", "INSERT", "2025-03-01 12:00:00", "Tabela A"));
        auditorias.add(new Auditoria("user2", "UPDATE", "2025-03-01 14:30:00", "Tabela B"));
        auditorias.add(new Auditoria("user3", "DELETE", "2025-03-01 16:45:00", "Tabela C"));

        return auditorias;
    }
}
