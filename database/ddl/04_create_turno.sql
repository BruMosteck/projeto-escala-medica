CREATE TABLE turno (
    id_turno INTEGER PRIMARY KEY AUTOINCREMENT,
    nome TEXT NOT NULL,
    hora_inicio TEXT NOT NULL,
    hora_fim TEXT NOT NULL,
    duracao_horas INTEGER NOT NULL
);