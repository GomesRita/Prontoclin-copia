package com.application.SpringProntoClin.domain;

import com.application.SpringProntoClin.DTO.RequestAgenda;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "agenda")
@Table(name = "agenda")
public class Agenda{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "agenda_seq_generator")
    @SequenceGenerator(name = "agenda_seq_generator", sequenceName = "agenda_SEQ", allocationSize = 1)
    private long idagenda;

    @ManyToOne
    @JoinColumn(name = "iduser", referencedColumnName = "iduser")
    private ProfissionalSaude profissionalSaude;

    @Column(name = "dataconsulta")
    private Date dataconsulta;

    @Column(name = "situacao")
    private String situacao;

    public Agenda(RequestAgenda requestAgenda) {
        this.profissionalSaude = new ProfissionalSaude();
        this.profissionalSaude.setIduser(requestAgenda.idProfissionalSaude());
        this.dataconsulta = requestAgenda.dataconsulta();
        this.situacao = requestAgenda.situacao();
    }
}
