package com.example.controller;

import com.example.model.Auditoria;
import java.util.List;

public class AuditoriaController {

    private AuditoriaDAO auditoriaDAO = new AuditoriaDAO();

    public List<Auditoria> carregarAuditoria() {
        return auditoriaDAO.buscarAuditorias();
    }
}
