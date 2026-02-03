-- Criação do banco de dados (se não existir)
CREATE SEQUENCE  usuario_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS usuario (
    iduser bigint DEFAULT nextval('usuario_seq') PRIMARY KEY,
    senha varchar(100) NOT NULL,
    email varchar(100) NOT NULL,
    userrole TEXT NOT NULL
);