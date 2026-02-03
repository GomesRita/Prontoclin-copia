package com.application.SpringProntoClin.DTO;

import com.application.SpringProntoClin.enums.UsuarioRole;

public record RequestUsuario(String email, String senha, UsuarioRole userrole) {
}
