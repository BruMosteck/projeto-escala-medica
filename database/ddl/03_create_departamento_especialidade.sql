CREATE TABLE departamento_especialidade (
    id_departamento INTEGER NOT NULL,
    id_especialidade INTEGER NOT NULL,
    PRIMARY KEY (id_departamento, id_especialidade),
    FOREIGN KEY (id_departamento) REFERENCES departamento(id_departamento),
    FOREIGN KEY (id_especialidade) REFERENCES especialidade(id_especialidade)
);