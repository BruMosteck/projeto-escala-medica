-- =====================================================================
-- 05_create_alocacao.sql
-- Projeto Escala Médica | Tabela: alocacao (o coração da escala)
-- Médico + departamento + dia da semana + turno.
-- Dependências: medico (já existente), departamento, turno
--
-- RN01 - Sem conflito de horário -> UNIQUE (id_medico, dia_semana, id_turno)
-- RN04 - Dia da semana válido     -> CHECK (dia_semana BETWEEN 1 AND 7)
--        1 = segunda ... 7 = domingo (ISO 8601)
-- RN06 - Integridade referencial  -> FKs NOT NULL p/ medico, departamento, turno
-- RN02 e RN03 são verificadas por consulta (database/queries) e na
-- camada de serviço (Aula 2).
-- =====================================================================
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS alocacao (
    id_alocacao     INTEGER PRIMARY KEY AUTOINCREMENT,
    id_medico       INTEGER NOT NULL REFERENCES medico (id_medico),             -- RN06
    id_departamento INTEGER NOT NULL REFERENCES departamento (id_departamento), -- RN06
    id_turno        INTEGER NOT NULL REFERENCES turno (id_turno),               -- RN06
    dia_semana      INTEGER NOT NULL
        CONSTRAINT ck_alocacao_dia_semana CHECK (dia_semana BETWEEN 1 AND 7),  -- RN04

    -- RN01: um médico não pode estar em dois lugares no mesmo dia e turno
    CONSTRAINT uq_alocacao_medico_dia_turno
        UNIQUE (id_medico, dia_semana, id_turno)
);
