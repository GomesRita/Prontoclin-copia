package com.application.SpringProntoClin.DTO;

import com.application.SpringProntoClin.domain.ProfissionalSaude;
import com.application.SpringProntoClin.enums.UsuarioRole;

public record RequestProfissionalSaude(Long iduser, String nomeProfissionalSaude, String cpfProfissionalSaude, String especialidadeMedica, String telefoneProfissionalSaude, String CRM, String status, String email, String senha, UsuarioRole userrole) {

    public RequestProfissionalSaude(ProfissionalSaude profissionalSaude) {
        this(
                profissionalSaude.getIduser(),
                profissionalSaude.getNomeprofissionalsaude(),
                profissionalSaude.getCpfprofissionalsaude(),
                profissionalSaude.getEspecialidademedica(),
                profissionalSaude.getTelefoneprofissionalsaude(),
                profissionalSaude.getCRM(),
                profissionalSaude.getStatus(),
                profissionalSaude.getEmail(),
                profissionalSaude.getSenha(),
                profissionalSaude.getUserrole());
    }

}
