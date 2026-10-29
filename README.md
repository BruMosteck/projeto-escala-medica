<p align="center">
  <img src="docs/logo.png" alt="EscalaMed Logo" width="240"/>
</p>

<h1 align="center">🏥 EscalaMed — Gestão de Escala Médica</h1>

<p align="center">
  <strong>Parte 1: Modelagem, Estrutura de Repositório e Scripts SQL</strong>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Banco%20de%20Dados-SQLite-003B57?style=for-the-badge&logo=sqlite&logoColor=white" alt="SQLite Badge"/>
  <img src="https://img.shields.io/badge/Estrutura-GitHub%20Organizado-0d5c75?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Organized Badge"/>
  <img src="https://img.shields.io/badge/Status-Parte%201%20Conclu%C3%ADda-success?style=for-the-badge&color=2ea44f" alt="Status Badge"/>
</p>

---

## 📋 Sobre o Projeto

O **EscalaMed** é um sistema projetado para solucionar um problema crítico de gestão hospitalar: a descentralização e a falta de visibilidade na escala de médicos de um grande hospital. Atualmente, a escala é feita em planilhas dispersas, impossibilitando localizar os médicos de prontidão de forma ágil. 

Com base em um cadastro pré-existente de médicos em **SQLite**, o projeto estende a base de dados para modelar e automatizar a escala semanal de múltiplos prédios e departamentos (UTI, Ambulatórios, Centro Cirúrgico, Pronto Atendimento), integrando restrições de carga horária e regras de compatibilidade de especialidades.

> **🎯 Pergunta Central:**  
> *"Em qual prédio e departamento o médico X está no dia Y, turno Z?"*

---

## 👥 Equipe de Desenvolvimento

Esta solução foi modelada e implementada pelos seguintes engenheiros:

*   👤 **Bruno Simcka Mosteck** — [GitHub](https://github.com/BruMosteck)
*   👤 **Miguel Freitas Vieira** — [GitHub](https://github.com/MiguelVFreitas) 
*   👤 **Henrique Pinheiro Lima** — [GitHub](https://github.com/PinheiroLim) 

**Disciplina**: Banco de Dados | **Professor**: Stelvio Barboza

---

## 📂 Estrutura do Repositório

O projeto segue à risca a organização exigida, garantindo que o banco de dados possa ser evoluído e integrado de forma fluida nas próximas etapas.

```text
projeto-escala-medica/
├── README.md               # Este arquivo de documentação e guia
├── database/               # Todo o ecossistema de banco de dados
│   ├── base/               # Base de dados fornecida
│   │   └── hospital.sqlite
│   ├── ddl/                # Scripts de criação das tabelas (Data Definition Language)
│   │   ├── 01_create_predio.sql
│   │   ├── 02_create_departamento.sql
│   │   ├── 03_create_departamento_especialidade.sql
│   │   ├── 04_create_turno.sql
│   │   └── 05_create_alocacao.sql
│   ├── dml/                # Cargas e povoamento inicial (Data Manipulation Language)
│   │   ├── 01_insert_predios_departamentos.sql
│   │   ├── 02_insert_turnos.sql
│   │   └── 03_insert_escala_semanal.sql
│   └── queries/            # Consultas de validação e relatórios requeridos
│       ├── q1_escala_medico.sql
│       ├── q2_medicos_predio_quarta.sql
│       ├── q3_medicos_sem_alocacao.sql
│       ├── q4_validacao_especialidade.sql
│       ├── q5_validacao_carga_horaria.sql
│       └── q6_quantitativo_por_departamento.sql
└── docs/                   # Planejamento, diagramas e identidade visual
    ├── diagramas/          # Modelos estruturais (DER e Lógico)
    │   ├── der_conceitual.png
    │   └── modelo_logico.png
    └── logo.png            # Identidade do EscalaMed