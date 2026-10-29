-- =====================================================================
-- 01_create_predio.sql
-- Projeto Escala Médica | Tabela: predio
-- Bloco, prédio ou unidade física do hospital.
-- Dependências: nenhuma
-- =====================================================================
PRAGMA foreign_keys = ON;

CREATE TABLE IF NOT EXISTS predio (
    id_predio   INTEGER PRIMARY KEY AUTOINCREMENT,
    nome        TEXT    NOT NULL UNIQUE,
    endereco    TEXT
);
