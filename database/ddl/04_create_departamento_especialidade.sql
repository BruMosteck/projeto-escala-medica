-- =====================================================================
-- 04_create_departamento_especialidade.sql
-- Projeto Escala Médica | Tabela associativa: departamento_especialidade
-- Resolve o N:N "quais especialidades podem atuar em cada departamento".
-- Dependências: departamento, especialidade (tabela já existente)
--
-- PK composta pelas duas FKs (não há id_departamento_especialidade).
-- Base para a consulta de validação da RN02 (especialidade compatível).
-- =====================================================================
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS departamento_especialidade (
    id_departamento  INTEGER NOT NULL REFERENCES departamento (id_departamento),
    id_especialidade INTEGER NOT NULL REFERENCES especialidade (id_especialidade),

    PRIMARY KEY (id_departamento, id_especialidade)
);

