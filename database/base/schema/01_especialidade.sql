-- =====================================================================
-- 01_especialidade.sql
-- Projeto Escala Médica | Tabela: especialidade
-- ATENÇÃO: tabela JÁ EXISTENTE em hospital.sqlite (fornecida pelo professor).
-- Arquivo apenas de REFERÊNCIA/DOCUMENTAÇÃO: não executar sobre hospital.sqlite.
-- Estrutura copiada exatamente do banco fornecido.
-- Dependências: nenhuma
-- =====================================================================

CREATE TABLE especialidade (
    id_especialidade INTEGER PRIMARY KEY AUTOINCREMENT,
    nome             TEXT NOT NULL UNIQUE
);
