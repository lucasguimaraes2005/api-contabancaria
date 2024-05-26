CREATE TABLE usuario (
    id VARCHAR NOT NULL PRIMARY KEY,
    nome VARCHAR(150) NOT NULL,
    sexo CHAR(1) NOT NULL,
    CPF CHAR(14) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    senha VARCHAR(10) NOT NULL
);

CREATE TABLE contabancaria (
    id VARCHAR NOT NULL PRIMARY KEY,
    id_proprietario VARCHAR NOT NULL,
    numeroconta DOUBLE PRECISION NOT NULL,
    saldoatual DOUBLE PRECISION NOT NULL,
    datahora_cadastro TIMESTAMP NOT NULL,
    FOREIGN KEY (id_proprietario) REFERENCES usuario(id)
);

CREATE TABLE transacao (
    id VARCHAR NOT NULL PRIMARY KEY,
    id_contabancaria VARCHAR NOT NULL,
    id_conta_recebedora VARCHAR,
    valor_transacao DOUBLE PRECISION NOT NULL,
    tipo_transacao VARCHAR(500),
    datahora_transacao TIMESTAMP NOT NULL,
    FOREIGN KEY (id_contabancaria) REFERENCES contabancaria(id),
    FOREIGN KEY (id_conta_recebedora) REFERENCES contabancaria(id)
);
