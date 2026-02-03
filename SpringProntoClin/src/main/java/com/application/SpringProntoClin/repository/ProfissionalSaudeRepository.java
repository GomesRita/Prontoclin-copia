package com.application.SpringProntoClin.repository;

import com.application.SpringProntoClin.domain.ProfissionalSaude;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalSaudeRepository extends JpaRepository<ProfissionalSaude, Long> {

    Optional<ProfissionalSaude> findById(Long idprofissionalSaude);
    Optional<ProfissionalSaude> findProfissionalSaudeByNomeprofissionalsaude(String nomeprofissionalsaude);
    List<ProfissionalSaude> findProfissionalSaudeByStatus(String statusProfissionalsaude);
    List<ProfissionalSaude> findAll();
}
