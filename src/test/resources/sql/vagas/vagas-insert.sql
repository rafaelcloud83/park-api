INSERT INTO USUARIOS (id, username, password, role) VALUES (100, 'admin@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_ADMIN');
INSERT INTO USUARIOS (id, username, password, role) VALUES (101, 'maria@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');
INSERT INTO USUARIOS (id, username, password, role) VALUES (102, 'joao@email.com', '$2a$10$xihFxLv3NqzQbLgwJc5FaOLhd7lkPi8pQHfMfQN8rMeMbspDK0BUi', 'ROLE_CLIENTE');

INSERT INTO VAGAS (id, codigo, status) VALUES (10, 'A-01', 'LIVRE');
INSERT INTO VAGAS (id, codigo, status) VALUES (20, 'A-02', 'LIVRE');
INSERT INTO VAGAS (id, codigo, status) VALUES (30, 'A-03', 'OCUPADA');
INSERT INTO VAGAS (id, codigo, status) VALUES (40, 'A-04', 'LIVRE');