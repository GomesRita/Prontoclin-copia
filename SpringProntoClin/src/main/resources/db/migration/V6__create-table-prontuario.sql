CREATE  SEQUENCE prontuario_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS prontuario (
    idprontuario bigint DEFAULT nextval('prontuario_seq') PRIMARY KEY,
    idpaciente bigint NOT NULL,
    idconsulta bigint NOT NULL,
    queixaprinciapal text NOT NULL,
    historicomedico text,
    alergias text,
    ultimaatualizacao date NOT NULL,
    diagnostico text,
    situacaotramento varchar(255),
    prescricaomedica text,
    CONSTRAINT fk_prontuario_paciente
        FOREIGN KEY (idpaciente)
        REFERENCES paciente(iduser)
        ON DELETE CASCADE,
    CONSTRAINT fk_prontuario_consulta
        FOREIGN KEY (idconsulta)
        REFERENCES consulta(idconsulta)
        ON DELETE CASCADE
);
