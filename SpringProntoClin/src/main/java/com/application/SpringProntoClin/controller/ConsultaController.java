package com.application.SpringProntoClin.controller;

import com.application.SpringProntoClin.DTO.RequestConsulta;
import com.application.SpringProntoClin.domain.Agenda;
import com.application.SpringProntoClin.domain.Consulta;
import com.application.SpringProntoClin.domain.Paciente;
import com.application.SpringProntoClin.domain.ProfissionalSaude;
import com.application.SpringProntoClin.repository.AgendaRepository;
import com.application.SpringProntoClin.repository.ConsultaRepository;
import com.application.SpringProntoClin.repository.ProfissionalSaudeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalSaudeRepository profissionalSaudeRepository;
    @Autowired
    private AgendaRepository agendaRepository;

    @PostMapping
    public ResponseEntity<?> registrarConsulta(@RequestBody RequestConsulta consulta) {
        // Busca o profissional de saúde pelo nome
        ProfissionalSaude profissional = profissionalSaudeRepository.findProfissionalSaudeByNomeprofissionalsaude(consulta.nomeProfissionalSaude())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        // Verifica se o profissional está ativo
        if (Objects.equals(profissional.getStatus(), "INATIVO")) {
            return ResponseEntity.badRequest().body("Profissional não está ativo.");
        }

        // Obtém o paciente autenticado
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Paciente paciente = (Paciente) authentication.getPrincipal();

        // Verifica se o paciente já tem uma consulta agendada para o mesmo dia
        if (!consultaRepository.findConsultaByIdpacienteAndDataconsulta(paciente.getIduser(), consulta.dataConsulta()).isEmpty()) {
            return ResponseEntity.badRequest().body("Paciente já possui uma consulta agendada para este dia");
        }

        // Verifica se o horário está disponível na agenda do profissional
        Optional<Agenda> agendaExistente = agendaRepository.findByProfissionalSaudeAndDataconsulta(profissional, consulta.dataConsulta());

        if (agendaExistente.isPresent()) {
            Agenda agenda = agendaExistente.get();
            if (agenda.getSituacao().equals("indisponivel")) {
                return ResponseEntity.badRequest().body("Profissional não disponível para esta data e hora");
            }
            // Marca o horário como indisponível
            agenda.setSituacao("indisponivel");
            agendaRepository.save(agenda);

            // Cria e salva a nova consulta
            Consulta newConsulta = new Consulta();
            newConsulta.setIdpaciente(paciente.getIduser());
            newConsulta.setNomepaciente(paciente.getNomepaciente());
            newConsulta.setNomesocial(paciente.getNomesocial());
            newConsulta.setIdprofissionalsaude(profissional.getIduser());
            newConsulta.setNomeprofissionalsaude(profissional.getNomeprofissionalsaude());
            newConsulta.setEspecialidademedica(profissional.getEspecialidademedica());
            newConsulta.setDataconsulta(consulta.dataConsulta());

            consultaRepository.save(newConsulta);

            return new ResponseEntity<>(newConsulta, HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body("Horário não encontrado na agenda do profissional");
        }
    }

    @GetMapping("/profissional/consultas")
    public ResponseEntity<List<Agenda>> getConsultasByProfissionalId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        ProfissionalSaude profissionalSaude = (ProfissionalSaude) principal;
        List<Agenda> agenda = agendaRepository.findAgendaByProfissionalSaude(profissionalSaude);
        if (agenda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agenda);
    }

    @PostMapping("/agendaprofissional")
    public ResponseEntity<List<Agenda>> getAgendasByProfissional(@RequestBody ProfissionalSaude profissionalSaude) {
        ProfissionalSaude profissional = profissionalSaudeRepository.findProfissionalSaudeByNomeprofissionalsaude(profissionalSaude.getNomeprofissionalsaude()).orElseThrow(RuntimeException::new);
        System.out.println(profissional);
        List<Agenda> agenda = agendaRepository.findAgendaBySituacaoAndProfissionalSaude("disponivel", profissional);
        if (agenda.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(agenda);
    }


    @GetMapping("/paciente/consultas")
    public ResponseEntity<List<Consulta>> getConsultasByPacienteId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Paciente paciente = (Paciente) principal;
        List<Consulta> consultas = consultaRepository.findConsultaByIdpaciente(paciente.getIduser());
        if (consultas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(consultas);
    }

    @PutMapping("/atualizarConsulta")
    public ResponseEntity<Object> atualizarConsulta(@RequestBody Consulta consulta) {
        ProfissionalSaude profissional = profissionalSaudeRepository
                .findProfissionalSaudeByNomeprofissionalsaude(consulta.getNomeprofissionalsaude())
                .orElseThrow(() -> new RuntimeException("Profissional de Saúde não encontrado"));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Paciente paciente = (Paciente) authentication.getPrincipal();

        Consulta novaConsulta = consultaRepository.findById(consulta.getIdconsulta())
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));
        if (!novaConsulta.getIdpaciente().equals(paciente.getIduser())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        Optional<Agenda> agendaAntiga = agendaRepository
                .findAgendaByDataconsultaAndProfissionalSaude(novaConsulta.getDataconsulta(), profissional);
        if (agendaAntiga.isPresent()) {
            Agenda agenda = agendaAntiga.get();
            if (agenda.getSituacao().equals("indisponivel")) {
                agenda.setSituacao("disponivel");
                agendaRepository.save(agenda);
            }
        }
        Optional<Agenda> agendaNova = agendaRepository
                .findByProfissionalSaudeAndDataconsulta(profissional, consulta.getDataconsulta());
        if (agendaNova.isPresent()) {
            Agenda agenda = agendaNova.get();
            if (agenda.getSituacao().equals("disponivel")) {
                agenda.setSituacao("indisponivel");
                agendaRepository.save(agenda);
            }
        }
        novaConsulta.setDataconsulta(consulta.getDataconsulta());
        consultaRepository.save(novaConsulta);
        return ResponseEntity.ok().body(novaConsulta);
    }

    @PutMapping("/deletarConsulta")
    public ResponseEntity<?> deleteConsulta(@RequestBody Consulta consulta) {
        ProfissionalSaude profissional = profissionalSaudeRepository.findProfissionalSaudeByNomeprofissionalsaude(consulta.getNomeprofissionalsaude()).orElseThrow(RuntimeException::new);
        Consulta newConsulta = consultaRepository.findConsultaByDataconsultaAndIdprofissionalsaude(consulta.getDataconsulta(), profissional.getIduser());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Paciente paciente = (Paciente) principal;
        if (newConsulta == null) {
            throw new RuntimeException("Consulta não encontrada.");
        }
        Optional<Agenda> agendaExistente = agendaRepository.findByProfissionalSaudeAndDataconsulta(profissional, consulta.getDataconsulta());
        if (agendaExistente.isPresent()) {
            Agenda agenda = agendaExistente.get();
            if (agenda.getSituacao().equals("indisponivel")) {
                agenda.setSituacao("disponivel");
                newConsulta.setIdpaciente(paciente.getIduser());
                newConsulta.setIdprofissionalsaude(profissional.getIduser());
                newConsulta.setNomeprofissionalsaude(profissional.getNomeprofissionalsaude());
                newConsulta.setNomepaciente(paciente.getNomepaciente());
                newConsulta.setNomesocial(paciente.getNomesocial());
                newConsulta.setEspecialidademedica(profissional.getEspecialidademedica());
                consultaRepository.delete(newConsulta);
                agendaRepository.save(agenda);
                return new ResponseEntity(HttpStatus.NO_CONTENT);
            }
        }
        return ResponseEntity.badRequest().body("Erro ao deletar a consulta");
    }
}
