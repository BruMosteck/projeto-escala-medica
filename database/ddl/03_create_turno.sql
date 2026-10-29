-- =====================================================================
-- 03_create_turno.sql
-- Projeto Escala Médica | Tabela: turno
-- Faixas de horário: manhã 07h-13h, tarde 13h-19h, noite 19h-07h.
-- Dependências: nenhuma
--
-- Horários em TEXT no formato ISO 'HH:MM' (SQLite não tem TIME nativo).
-- O CHECK exige exatamente 'HH:MM' com zero à esquerda (GLOB) e hora
-- até 23:59 (< '24:00'); o formato fixo garante a ordenação correta.
-- duracao_horas é armazenada porque o turno da noite atravessa a
-- meia-noite (19:00 -> 07:00 = 12h); facilita a validação da RN03.
-- =====================================================================
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS turno (
    id_turno      INTEGER PRIMARY KEY AUTOINCREMENT,
    nome          TEXT    NOT NULL UNIQUE,   -- 'Manhã', 'Tarde', 'Noite'
    hora_inicio   TEXT    NOT NULL
        CHECK (hora_inicio GLOB '[0-2][0-9]:[0-5][0-9]' AND hora_inicio < '24:00'), -- Condicao para aceitar apenas o formato HH:MM (00:00 a 23:59), com zero à esquerda 
    hora_fim      TEXT    NOT NULL
        CHECK (hora_fim    GLOB '[0-2][0-9]:[0-5][0-9]' AND hora_fim    < '24:00'), -- Condicao para aceitar apenas o formato HH:MM (00:00 a 23:59), com zero à esquerda 
    duracao_horas INTEGER NOT NULL
        CHECK (duracao_horas BETWEEN 1 AND 24)
);
