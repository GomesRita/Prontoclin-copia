package com.application.SpringProntoClin.domain;

import com.application.SpringProntoClin.DTO.RequestProntuario;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.envers.Audited;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idProntuario")
@Audited
@Entity (name = "prontuario")
@Table (name = "prontuario")
public class Prontuario {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "prontuario_seq_generator")
    @SequenceGenerator(name = "prontuario_seq_generator", sequenceName = "prontuario_SEQ", allocationSize = 1)
    private Long idProntuario;

    @ManyToOne
    @JoinColumn(name = "idpaciente", referencedColumnName = "iduser")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "idconsulta", referencedColumnName = "idconsulta")
    private Consulta consulta;

    private String historicomedico;
    private String alergias;
    private Date ultimaatualizacao;
    private String queixaprinciapal;
    private String diagnostico;
    private String situacaotramento;
    private String prescricaomedica;

    public Prontuario(RequestProntuario prontuario ) {
        this.idProntuario = prontuario.idprontuario();
        this.historicomedico = prontuario.historicomedico();
        this.alergias = prontuario.alergias();
        this.ultimaatualizacao = prontuario.ultimaatualizacao();
        this.queixaprinciapal = prontuario.queixaprincipal();
        this.diagnostico = prontuario.diagnostico();
        this.situacaotramento = prontuario.situacaotratamento();
        this.prescricaomedica = prontuario.prescricaomedica();
    }

}
