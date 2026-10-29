PRAGMA foreign_keys = ON;

INSERT INTO turno (id_turno, nome, hora_inicio, hora_fim, duracao_horas) VALUES
    (1, 'Manhã', '07:00', '13:00',  6),
    (2, 'Tarde', '13:00', '19:00',  6),
    (3, 'Noite', '19:00', '07:00', 12);