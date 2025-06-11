INSERT INTO USUARIOS (id, username, password, role)
    VALUES (100, 'adm@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_ADMIN');
INSERT INTO USUARIOS (id, username, password, role)
    VALUES (101, 'maria@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');
INSERT INTO USUARIOS (id, username, password, role)
    VALUES (102, 'joao@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');

INSERT INTO CLIENTES (id, nome, cpf, id_usuario) VALUES (21, 'Maria Santos', '38641465006', 101);
INSERT INTO CLIENTES (id, nome, cpf, id_usuario) VALUES (22, 'João Silva', '81315448009', 102);

INSERT INTO VAGAS (id, codigo, status) VALUES (100, 'A-01', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status) VALUES (200, 'A-02', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status) VALUES (300, 'A-03', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status) VALUES (400, 'A-04', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status) VALUES (500, 'A-05', 'OCUPADA');

INSERT INTO CLIENTES_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20250513-101300', 'FIT-1010', 'FIAT', 'PALIO', 'VERDE', '2025-05-13 10:13:00', 22, 100);
INSERT INTO CLIENTES_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20250514-101400', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2025-05-14 10:14:00', 21, 200);
INSERT INTO CLIENTES_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20250515-101500', 'FIT-1030', 'FIAT', 'PALIO', 'VERDE', '2025-05-15 10:15:00', 22, 300);
INSERT INTO CLIENTES_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20250516-101600', 'SIE-1040', 'FIAT', 'SIENA', 'VERDE', '2025-05-16 10:16:00', 21, 400);
INSERT INTO CLIENTES_VAGAS (numero_recibo, placa, marca, modelo, cor, data_entrada, id_cliente, id_vaga)
    VALUES ('20250517-101700', 'SIE-1050', 'FIAT', 'SIENA', 'VERDE', '2025-05-17 10:17:00', 22, 500);