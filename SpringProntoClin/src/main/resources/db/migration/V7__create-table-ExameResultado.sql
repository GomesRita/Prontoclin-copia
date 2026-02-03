CREATE  SEQUENCE exameresultados_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS  examesresultados  (
     idexameresultado bigint DEFAULT nextval('exameresultados_seq') PRIMARY KEY,
     idprontuario int NOT NULL,
     tipoexame varchar(100) NOT NULL,
     datarealizacao  date,
     exame varchar(255),
     resultado varchar(255),
    CONSTRAINT  idprontuario
        FOREIGN KEY ( idprontuario )
        REFERENCES  prontuario ( idprontuario )
        ON DELETE CASCADE
);
