DROP TABLE IF EXISTS cliente;

CREATE TABLE cliente (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(255),
    email VARCHAR(255)
);