package com.application.SpringProntoClin.DTO;

import com.application.SpringProntoClin.domain.Administrador;
import com.application.SpringProntoClin.enums.UsuarioRole;

public record RequestAdministrador(Long iduser, String nome, String cpf, String email, String senha, UsuarioRole userrole) {

    public RequestAdministrador(Administrador administrador) {
        this(
                administrador.getIduser(),
                administrador.getNome(),
                administrador.getCpf(),
                administrador.getEmail(),
                administrador.getSenha(),
                administrador.getUserrole());
    }
}
