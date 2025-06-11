INSERT INTO USUARIOS (id, username, password, role) VALUES (100, 'adm@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_ADMIN');
INSERT INTO USUARIOS (id, username, password, role) VALUES (101, 'maria@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');
INSERT INTO USUARIOS (id, username, password, role) VALUES (102, 'joao@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');
INSERT INTO USUARIOS (id, username, password, role) VALUES (103, 'jose@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');

INSERT INTO CLIENTES (id, nome, cpf, id_usuario) VALUES (10, 'Maria Santos', '38641465006', 101);
INSERT INTO CLIENTES (id, nome, cpf, id_usuario) VALUES (20, 'João Silva', '81315448009', 102);