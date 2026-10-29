PRAGMA foreign_keys = ON;

INSERT INTO predio (nome) VALUES ('Bloco Principal'), ('Anexo de Especialidades');

INSERT INTO departamento (nome, id_predio) VALUES 
('UTI', 1), ('Pronto Atendimento', 1), ('Ambulatório', 2);

INSERT INTO departamento_especialidade (id_departamento, id_especialidade) VALUES 
(1, 1), (2, 2);