package com.application.SpringProntoClin.controller;

import com.application.SpringProntoClin.DTO.*;
import com.application.SpringProntoClin.domain.Administrador;
import com.application.SpringProntoClin.domain.Paciente;
import com.application.SpringProntoClin.domain.ProfissionalSaude;
import com.application.SpringProntoClin.domain.Usuario;
import com.application.SpringProntoClin.enums.UsuarioRole;
import com.application.SpringProntoClin.infra.TokenService;
import com.application.SpringProntoClin.repository.AdmRepository;
import com.application.SpringProntoClin.repository.PacienteRepository;
import com.application.SpringProntoClin.repository.ProfissionalSaudeRepository;
import com.application.SpringProntoClin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private AdmRepository admRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalSaudeRepository profissionalSaudeRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody RequestAuthentication authentication) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authentication.email(), authentication.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        Usuario usuario = (Usuario) auth.getPrincipal();
        return ResponseEntity.ok(new RequestLogin(token, usuario.getUserrole()));
    }

    @PostMapping("/register/adm")
    public ResponseEntity registerAdm(@RequestBody RequestAdministrador adm) {
        if(this.usuarioRepository.findByEmail(adm.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(adm.senha());

        Administrador administrador = new Administrador(adm);
        administrador.setSenha(encryptPassword);
        this.admRepository.save(administrador);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/register/paciente")
    public ResponseEntity registerPaciente(@RequestBody RequestPaciente paciente) {
        if(this.usuarioRepository.findByEmail(paciente.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(paciente.senha());

        Paciente newpaciente = new Paciente(paciente);
        newpaciente.setSenha(encryptPassword);
        pacienteRepository.save(newpaciente);

        return ResponseEntity.ok().build();
    }
    @PostMapping("/register/prosaude")
    public ResponseEntity registerProSaude(@RequestBody RequestProfissionalSaude prosaude) {
        if(this.usuarioRepository.findByEmail(prosaude.email()) != null) return ResponseEntity.badRequest().build();

        String encryptPassword = new BCryptPasswordEncoder().encode(prosaude.senha());

        ProfissionalSaude newprosaude = new ProfissionalSaude(prosaude);
        newprosaude.setSenha(encryptPassword);
        profissionalSaudeRepository.save(newprosaude);

        return ResponseEntity.ok().build();
    }
}
