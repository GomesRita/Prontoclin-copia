CREATE  SEQUENCE profissionalsaude_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS profissionalsaude (
	iduser bigint DEFAULT nextval('profissionalsaude_seq') PRIMARY KEY,
	nomeprofissionalsaude varchar(100) NOT NULL,
	cpfprofissionalsaude varchar(11) NOT NULL,
	especialidademedica varchar(100) NOT NULL,
	telefoneprofissionalsaude  varchar(100) NOT NULL,
	CRM  varchar(100) NOT NULL,
    status varchar(100) NOT NULL,
    CONSTRAINT fk_usuario
        FOREIGN KEY (iduser)
        REFERENCES usuario(iduser)
        ON DELETE CASCADE
);