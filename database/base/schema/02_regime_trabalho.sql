-- =====================================================================
-- 02_regime_trabalho.sql
-- Projeto Escala Médica | Tabela: regime_trabalho
-- ATENÇÃO: tabela JÁ EXISTENTE em hospital.sqlite (fornecida pelo professor).
-- Arquivo apenas de REFERÊNCIA/DOCUMENTAÇÃO: não executar sobre hospital.sqlite.
-- Estrutura copiada exatamente do banco fornecido.
-- Dependências: nenhuma
-- =====================================================================

CREATE TABLE regime_trabalho (
    id_regime             INTEGER PRIMARY KEY AUTOINCREMENT,
    descricao             TEXT    NOT NULL UNIQUE,
    carga_horaria_semanal INTEGER NOT NULL CHECK (carga_horaria_semanal > 0)
);
