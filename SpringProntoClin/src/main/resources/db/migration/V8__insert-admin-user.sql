INSERT INTO usuario (senha, email, userrole)
VALUES ('$2a$12$LYEcH.n7HssfqUr4wDz4keNs15CMhAz.yqkCT/0cqwuYs8xKU3z0e', 'admin@example.com', 'ADMIN');

INSERT INTO administrador (iduser, nome, cpf)
VALUES (currval('usuario_seq'), 'Administrador', '12345678900');