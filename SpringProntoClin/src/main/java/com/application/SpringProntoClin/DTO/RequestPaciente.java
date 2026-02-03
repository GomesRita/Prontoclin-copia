package com.application.SpringProntoClin.DTO;

import com.application.SpringProntoClin.domain.Paciente;
import com.application.SpringProntoClin.enums.UsuarioRole;

import java.util.Date;

public record RequestPaciente(Long iduser, String nomePaciente, String nomeSocial, String telefonePaciente, String cpfPaciente, Date dataNascimento, String sexoPaciente, String email, String senha, UsuarioRole userrole) {

    public RequestPaciente(Paciente paciente) {
        this(
                paciente.getIduser(),
                paciente.getNomepaciente(),
                paciente.getNomesocial(),
                paciente.getTelefonepaciente(),
                paciente.getCpfpaciente(),
                paciente.getDatanascimento(),
                paciente.getSexopaciente(),
                paciente.getEmail(),
                paciente.getSenha(),
                paciente.getUserrole());
    }
}
