package com.application.SpringProntoClin.domain;

import com.application.SpringProntoClin.DTO.RequestConsulta;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idconsulta")
@Audited
@Entity (name = "consulta")
@Table (name = "consulta")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "consulta_seq_generator")
    @SequenceGenerator(name = "consulta_seq_generator", sequenceName = "consulta_SEQ", allocationSize = 1)
    private Long idconsulta;

    private Long idpaciente;
    private Long idprofissionalsaude;

    private String nomeprofissionalsaude;
    private String nomepaciente;
    private String nomesocial;
    private Date dataconsulta;
    private String especialidademedica;

    public Consulta(RequestConsulta requestConsulta) {
        this.idconsulta = requestConsulta.idConsulta();
        this.idpaciente = requestConsulta.idPaciente();
        this.idprofissionalsaude = requestConsulta.idProfissionalSaude();
        this.nomeprofissionalsaude = requestConsulta.nomeProfissionalSaude();
        this.nomepaciente = requestConsulta.nomePaciente();
        this.nomesocial = requestConsulta.nomeSocial();
        this.dataconsulta = requestConsulta.dataConsulta();
        this.especialidademedica = requestConsulta.especialidadeMedica();
    }

}
