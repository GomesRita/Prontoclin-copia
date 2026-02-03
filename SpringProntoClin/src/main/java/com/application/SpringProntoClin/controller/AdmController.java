package com.application.SpringProntoClin.controller;

import com.application.SpringProntoClin.DTO.RequestAdministrador;
import com.application.SpringProntoClin.domain.Administrador;
import com.application.SpringProntoClin.domain.Paciente;
import com.application.SpringProntoClin.repository.AdmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/adm")
public class AdmController {

    @Autowired
    private AdmRepository admRepository;

    @GetMapping("/me")
    public Administrador getAdm() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Administrador administrador = (Administrador) principal;
        return admRepository.findById(administrador.getIduser()).orElseThrow(() -> new RuntimeException("Administrador não encontrado"));
    }

    @PutMapping("/atualiza")
    public Administrador updateAdm(@RequestBody Administrador adm) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Administrador admin = (Administrador) principal;
        Administrador administrador = admRepository.findById(admin.getIduser()).orElseThrow(() -> new RuntimeException("Administrador não encontrado"));
        administrador.setNome(adm.getNome());
        administrador.setEmail(adm.getEmail());
        String encryptPassword = new BCryptPasswordEncoder().encode(adm.getSenha());
        administrador.setSenha(encryptPassword);

        return admRepository.save(administrador);

    }
}
