package com.application.SpringProntoClin.repository;

import com.application.SpringProntoClin.domain.Consulta;
import com.application.SpringProntoClin.domain.Prontuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProntuarioRepository extends JpaRepository<Prontuario, Long> {

    Prontuario findProntuarioByPaciente_Iduser(Long idPaciente);
    Prontuario findProntuarioByConsulta(Consulta consulta);
    List<Prontuario> findProntuarioByPaciente_Cpfpaciente(String cpfpaciente);
    Prontuario findTopByPaciente_IduserOrderByUltimaatualizacaoDesc(Long iduser);
}
