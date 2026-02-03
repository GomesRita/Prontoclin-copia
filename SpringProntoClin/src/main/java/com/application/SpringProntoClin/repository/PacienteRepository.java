package com.application.SpringProntoClin.repository;

import com.application.SpringProntoClin.domain.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long>{

    Paciente findPacienteByIduser(Long iduser);
}
