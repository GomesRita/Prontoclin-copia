package com.application.SpringProntoClin.DTO;

import com.application.SpringProntoClin.domain.Consulta;

import java.util.Date;

public record RequestConsulta(Long idConsulta, Long idPaciente, Long idProfissionalSaude, String nomeProfissionalSaude, String nomePaciente, String nomeSocial , Date dataConsulta, String especialidadeMedica) {

    public RequestConsulta(Consulta consulta) {
        this(consulta.getIdconsulta(), consulta.getIdpaciente(), consulta.getIdprofissionalsaude(), consulta.getNomeprofissionalsaude(),consulta.getNomepaciente(), consulta.getNomesocial() ,consulta.getDataconsulta(), consulta.getEspecialidademedica());
    }
}
