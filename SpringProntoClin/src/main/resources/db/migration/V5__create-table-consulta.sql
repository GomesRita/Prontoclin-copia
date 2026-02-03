CREATE  SEQUENCE consulta_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS consulta (
	idconsulta bigint DEFAULT nextval('consulta_seq') PRIMARY KEY,
	idpaciente bigint NOT NULL,
	idprofissionalsaude bigint NOT NULL,
	nomeprofissionalsaude varchar(100) NOT NULL,
	nomepaciente varchar(100) NOT NULL,
    nomesocial varchar(100),
	dataconsulta timestamp with time zone NOT NULL,
	especialidademedica varchar(100) NOT NULL,
    CONSTRAINT fk_consulta_paciente
        FOREIGN KEY (idpaciente)
        REFERENCES paciente(iduser)
        ON DELETE CASCADE,
    CONSTRAINT fk_consulta_profissionalsaude
        FOREIGN KEY (idprofissionalsaude)
        REFERENCES profissionalsaude(iduser)
        ON DELETE CASCADE
);
