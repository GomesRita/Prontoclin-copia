package com.application.SpringProntoClin.controller;

import com.application.SpringProntoClin.DTO.RequestPaciente;
import com.application.SpringProntoClin.domain.Consulta;
import com.application.SpringProntoClin.domain.Paciente;
import com.application.SpringProntoClin.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    @GetMapping("/me")
    public Paciente getPaciente() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Paciente paciente = (Paciente) principal;
        return pacienteRepository.findById(paciente.getIduser()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    @PutMapping("/atualiza")
    public Paciente updatePaciente(@RequestBody Paciente paciente) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Paciente userpaciente = (Paciente) principal;
        Paciente patient = pacienteRepository.findById(userpaciente.getIduser()).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        patient.setEmail(paciente.getEmail());
        String encryptPassword = new BCryptPasswordEncoder().encode(paciente.getSenha());
        patient.setSenha(encryptPassword);
        patient.setNomesocial(paciente.getNomesocial());
        patient.setTelefonepaciente(paciente.getTelefonepaciente());

        return pacienteRepository.save(patient);
    }
}
