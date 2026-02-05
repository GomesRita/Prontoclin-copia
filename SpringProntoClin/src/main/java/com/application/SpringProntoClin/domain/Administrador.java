package com.application.SpringProntoClin.domain;

import com.application.SpringProntoClin.DTO.RequestAdministrador;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Entity(name = "administrador")
@Table(name = "administrador")
@PrimaryKeyJoinColumn(name="iduser")
public class Administrador extends Usuario {

    @Column
    private String nome;

    @Column(unique = true)
    private String cpf;

    public Administrador(RequestAdministrador requestAdministrador) {
        super(requestAdministrador.email(), requestAdministrador.senha(), requestAdministrador.userrole());
        this.nome = requestAdministrador.nome();
        this.cpf = requestAdministrador.cpf();
    }

}
