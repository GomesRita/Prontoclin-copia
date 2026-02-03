CREATE  SEQUENCE  agenda_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS agenda (
    idagenda bigint DEFAULT nextval('agenda_seq') PRIMARY KEY,
    iduser bigint NOT NULL,
    dataconsulta timestamp NOT NULL,
    situacao varchar(20) NOT NULL,
    CONSTRAINT fk_iduser FOREIGN KEY (iduser)
    REFERENCES profissionalsaude(iduser)
    ON DELETE CASCADE
);