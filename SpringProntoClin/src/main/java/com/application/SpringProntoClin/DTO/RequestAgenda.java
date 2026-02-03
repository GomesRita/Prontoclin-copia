package com.application.SpringProntoClin.DTO;

import java.util.Date;

public record RequestAgenda(Long idProfissionalSaude, Date dataconsulta, String situacao) {

    public RequestAgenda {
        // Validações básicas
        if (idProfissionalSaude == null) {
            throw new IllegalArgumentException("ID do profissional de saúde não pode ser nulo");
        }
        if (dataconsulta == null) {
            throw new IllegalArgumentException("Data da consulta não pode ser nula");
        }
        if (situacao == null || situacao.isBlank()) {
            throw new IllegalArgumentException("Situação não pode ser nula ou vazia");
        }
    }
}
