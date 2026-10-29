-- =====================================================================
-- 03_medico.sql
-- Projeto Escala Médica | Tabela: medico
-- ATENÇÃO: tabela JÁ EXISTENTE em hospital.sqlite (fornecida pelo professor).
-- Arquivo apenas de REFERÊNCIA/DOCUMENTAÇÃO: não executar sobre hospital.sqlite.
-- Estrutura copiada exatamente do banco fornecido.
-- Dependências: especialidade, regime_trabalho
-- =====================================================================

CREATE TABLE medico (
    id_medico        INTEGER PRIMARY KEY AUTOINCREMENT,
    nome             TEXT    NOT NULL,
    crm              TEXT    NOT NULL UNIQUE,
    email            TEXT    NOT NULL UNIQUE,
    data_admissao    TEXT    NOT NULL,
    id_especialidade INTEGER NOT NULL REFERENCES especialidade(id_especialidade),
    id_regime        INTEGER NOT NULL REFERENCES regime_trabalho(id_regime)
);
