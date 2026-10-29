PRAGMA foreign_keys = ON;

INSERT INTO departamento (id_departamento, nome, andar, id_predio) VALUES
    (1, 'UTI',                       3, 1),
    (2, 'Pronto Atendimento',        0, 1),
    (3, 'Centro Cirúrgico',          2, 1),
    (4, 'Ambulatório de Ortopedia',  1, 2),
    (5, 'Ambulatório de Cardiologia',1, 2),
    (6, 'Ambulatório de Neurologia', 2, 2),
    (7, 'Maternidade',               1, 3),
    (8, 'Pediatria',                 2, 3),
    (9, 'Diagnóstico por Imagem',    0, 3);