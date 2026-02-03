package com.application.SpringProntoClin.repository;

import com.application.SpringProntoClin.domain.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdmRepository extends JpaRepository<Administrador, Long>{
}
