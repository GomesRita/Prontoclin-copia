CREATE  SEQUENCE paciente_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS paciente (
	iduser bigint DEFAULT nextval('paciente_seq') PRIMARY KEY,
	nomepaciente varchar(100) NOT NULL,
	nomesocial varchar(100),
	telefonepaciente varchar(50) NOT NULL,
	cpfpaciente varchar(100) NOT NULL,
	datanascimento date NOT NULL,
    CONSTRAINT fk_usuario
        FOREIGN KEY (iduser)
        REFERENCES usuario(iduser)
        ON DELETE CASCADE
);