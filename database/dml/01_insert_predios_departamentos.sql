PRAGMA foreign_keys = ON;

INSERT INTO predio (nome, endereco) VALUES 
('Bloco Principal', 'Av. Central, 100'), 
('Anexo de Especialidades', 'Av. Central, 102');

INSERT INTO departamento (nome, andar, id_predio) VALUES 
('UTI', 2, 1), 
('Pronto Atendimento', 0, 1), 
('Ambulatório', 1, 2);

INSERT INTO departamento_especialidade (id_departamento, id_especialidade) VALUES 
(1, 1), (2, 2);