package com.application.SpringProntoClin.controller;

import com.application.SpringProntoClin.domain.ProfissionalSaude;
import com.application.SpringProntoClin.repository.ProfissionalSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/profSaude")
public class ProfissionalSaudeController {

    @Autowired
    private ProfissionalSaudeRepository profissionalSaudeRepository;

    @GetMapping("/me")
    public ProfissionalSaude getProfissionalSaude() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        ProfissionalSaude profissionalSaude = (ProfissionalSaude) principal;
        return profissionalSaudeRepository.findById(profissionalSaude.getIduser()).orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado"));
    }

    @GetMapping("/profissionais")
    public ResponseEntity<List<ProfissionalSaude>> buscarProfissionaisAtivos() {
        List<ProfissionalSaude> profissionaisAtivos = profissionalSaudeRepository.findProfissionalSaudeByStatus("ATIVO");
        return ResponseEntity.ok(profissionaisAtivos);
    }

    @GetMapping("/AllProfissionais")
    public ResponseEntity<List<ProfissionalSaude>> buscarProfissionaisInativos() {
        List<ProfissionalSaude> profissionais = profissionalSaudeRepository.findAll();
        return ResponseEntity.ok(profissionais);
    }

    @PutMapping("/atualiza")
    public ProfissionalSaude updateProfissionalSaude(@RequestBody ProfissionalSaude profissionalSaude) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        ProfissionalSaude prosaude = (ProfissionalSaude) principal;
        ProfissionalSaude profSaude = profissionalSaudeRepository.findById(prosaude.getIduser()).orElseThrow(() -> new RuntimeException("Profissional de saúde não encontrado"));
        profSaude.setEmail(profissionalSaude.getEmail());
        String encryptPassword = new BCryptPasswordEncoder().encode(profissionalSaude.getSenha());
        profSaude.setSenha(encryptPassword);
        profSaude.setEspecialidademedica(profissionalSaude.getEspecialidademedica());
        profSaude.setTelefoneprofissionalsaude(profissionalSaude.getTelefoneprofissionalsaude());
        profSaude.setStatus("ATIVO");
        return profissionalSaudeRepository.save(profSaude);
    }

    @PutMapping("/StatusProfissional")
    public ResponseEntity<String>  atualizaProfSaude(@RequestBody ProfissionalSaude statusRequest) {
        Optional<ProfissionalSaude> profissionalSaude = profissionalSaudeRepository.findProfissionalSaudeByNomeprofissionalsaude(statusRequest.getNomeprofissionalsaude());
        if (profissionalSaude.isPresent()) {
            ProfissionalSaude proSaude = profissionalSaude.get();
            if (proSaude.getStatus().equals("ATIVO")) {
                proSaude.setStatus("INATIVO");
                profissionalSaudeRepository.save(proSaude);
                return ResponseEntity.ok("Status atualizado com sucesso.");
            } else {
                proSaude.setStatus("ATIVO");
                profissionalSaudeRepository.save(proSaude);
                return ResponseEntity.ok("Status atualizado com sucesso.");
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Profissional não encontrado.");
    }
}
