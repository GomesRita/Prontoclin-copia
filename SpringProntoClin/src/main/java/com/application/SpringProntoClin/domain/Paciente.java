package com.application.SpringProntoClin.domain;


import com.application.SpringProntoClin.DTO.RequestPaciente;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
@Entity(name = "paciente")
@Table(name = "paciente")
@PrimaryKeyJoinColumn(name="iduser")
public class Paciente extends Usuario {

    private String nomepaciente;
    private String nomesocial;

    @Column(unique = true)
    private String telefonepaciente;

    @Column(unique = true)
    private String cpfpaciente;

    private Date datanascimento;
    private String sexopaciente;


    public Paciente(RequestPaciente requestPaciente) {
        super(requestPaciente.email(), requestPaciente.senha(), requestPaciente.userrole());
        this.nomepaciente = requestPaciente.nomePaciente();
        this.nomesocial = requestPaciente.nomeSocial();
        this.telefonepaciente = requestPaciente.telefonePaciente();
        this.cpfpaciente = requestPaciente.cpfPaciente();
        this.datanascimento = requestPaciente.dataNascimento();
        this.sexopaciente = requestPaciente.sexoPaciente();
    }

}
