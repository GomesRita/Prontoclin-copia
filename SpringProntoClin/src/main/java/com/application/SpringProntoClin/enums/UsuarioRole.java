package com.application.SpringProntoClin.enums;

import org.apache.catalina.User;

public enum UsuarioRole {

    ADMIN("ADMIN"),
    PACIENTE("PACIENTE"),
    PROFSAUDE("PROFSAUDE");

    private String role;

    UsuarioRole(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
