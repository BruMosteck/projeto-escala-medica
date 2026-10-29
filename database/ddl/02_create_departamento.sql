-- =====================================================================
-- 02_create_departamento.sql
-- Projeto Escala Médica | Tabela: departamento
-- Setor dentro de um prédio (UTI, Pronto Atendimento, Ambulatório...).
-- Dependências: predio
--
-- RN05 - Todo departamento pertence a exatamente um prédio
--        -> id_predio NOT NULL + FOREIGN KEY
-- RN06 - Integridade referencial -> FOREIGN KEY
-- =====================================================================
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS departamento (
    id_departamento INTEGER PRIMARY KEY AUTOINCREMENT,
    nome            TEXT    NOT NULL,
    andar           INTEGER,
    id_predio       INTEGER NOT NULL REFERENCES predio (id_predio),
    -- não pode haver dois departamentos com o mesmo nome no mesmo prédio
    CONSTRAINT uq_departamento_predio_nome UNIQUE (id_predio, nome)
);
