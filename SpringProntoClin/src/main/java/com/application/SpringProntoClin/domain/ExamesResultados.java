package com.application.SpringProntoClin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idexameresultado")
@Entity (name = "examesresultados")
@Table(name = "examesresultados")
public class ExamesResultados {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exameresultado_seq_generator")
    @SequenceGenerator(name = "exameresultado_seq_generator", sequenceName = "exameresultado_SEQ", allocationSize = 1)
    private Long idExameResultado;

    private Long idProntuario;
    private String tipo;
    private Date dataRealizacao;
    private String exame;
    private String resultado;
}
