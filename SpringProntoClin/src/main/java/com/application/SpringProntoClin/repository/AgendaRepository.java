package com.application.SpringProntoClin.repository;

import com.application.SpringProntoClin.domain.Agenda;
import com.application.SpringProntoClin.domain.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface AgendaRepository extends JpaRepository<Agenda, Long> {

    List<Agenda> findAgendaByProfissionalSaude(ProfissionalSaude profissional);

    List<Agenda> findAgendaBySituacaoAndProfissionalSaude(String situacao, ProfissionalSaude profissionalSaude);

    Optional<Agenda> findAgendaByDataconsultaAndProfissionalSaude(Date dataconsulta, ProfissionalSaude profissionalSaude);
    Optional<Agenda> findByProfissionalSaudeAndDataconsulta(ProfissionalSaude profissional, Date date);
}
