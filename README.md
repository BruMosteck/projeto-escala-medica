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

Com base num cadastro pré-existente de médicos em **SQLite**, o projeto estende a base de dados para modelar e automatizar a escala semanal de múltiplos prédios e departamentos (UTI, Ambulatórios, Centro Cirúrgico, Pronto Atendimento), integrando restrições de carga horária e regras de compatibilidade de especialidades.

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

O projeto segue à risca a organização exigida, garantindo que a base de dados possa ser evoluída e integrada de forma fluida nas próximas etapas.

~~~text
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
~~~

---

## 📐 Modelagem e Regras de Negócio (RN)

Toda a modelagem estende as tabelas pré-existentes (`especialidade`, `medico`, `regime_trabalho`) e as relaciona com as novas entidades (`predio`, `departamento`, `departamento_especialidade`, `turno`, `alocacao`), garantindo a integridade dos dados por meio das seguintes regras:

| ID | Regra de Negócio (RN) | Implementação no Banco |
| :--- | :--- | :--- |
| **RN01** | **Sem conflito de horário** | Restrição de unicidade composta (`UNIQUE`) para que um médico não possua mais de uma alocação no mesmo dia e turno. |
| **RN02** | **Especialidade compatível** | Alocação restrita a departamentos que aceitam a especialidade do médico. *(Validada por consulta SQL `Q4`)* |
| **RN03** | **Carga do regime respeitada** | A soma das horas dos turnos alocados não pode ultrapassar o teto semanal do regime do médico. *(Validada por consulta SQL `Q5`)* |
| **RN04** | **Departamento tem prédio** | Todo departamento obrigatoriamente pertence a um prédio (`FK NOT NULL`). |
| **RN05** | **Dia da semana válido** | Dias representados de 1 (segunda) a 7 (domingo), no padrão ISO 8601, por meio de restrição `CHECK`. |
| **RN06** | **Integridade referencial** | Proibido alocar médicos, departamentos ou turnos inexistentes (`FOREIGN KEY`). |

---

## 📈 Diagramas do Projeto

Para conferir o mapeamento conceitual e a estrutura lógica desenvolvida para as novas tabelas, acesse os arquivos na pasta de documentação:

*   Visualizar [DER Conceitual](./docs/diagramas/der_conceitual.png)
*   Visualizar [Modelo Lógico](./docs/diagramas/modelo_logico.png)

---

## 🔍 Consultas Obrigatórias (`queries/`)

Para validar a integridade da escala, o repositório traz as 6 consultas complexas exigidas:

1.  **Q1 (`q1_escala_medico.sql`)**: Escala semanal completa de um médico (Dia, Turno, Prédio e Departamento).
2.  **Q2 (`q2_medicos_predio_quarta.sql`)**: Médicos presentes em determinado prédio nas quartas-feiras à tarde.
3.  **Q3 (`q3_medicos_sem_alocacao.sql`)**: Médicos que não possuem qualquer alocação na semana (`LEFT JOIN`).
4.  **Q4 (`q4_validacao_especialidade.sql`)**: Varredura por alocações em departamentos incompatíveis com a especialidade (deve retornar vazio).
5.  **Q5 (`q5_validacao_carga_horaria.sql`)**: Comparativo de horas alocadas vs. carga horária permitida pelo regime do médico, apontando quem excedeu.
6.  **Q6 (`q6_quantitativo_por_departamento.sql`)**: Volumetria diária e por departamento de médicos alocados (`GROUP BY`).

---

## 🚀 Como Executar o Projeto

Para rodar o projeto do zero e validar toda a estrutura, siga o passo a passo no seu cliente SQLite favorito (DBeaver, DB Browser for SQLite) ou via Terminal:

### 1. Garantir que as Chaves Estrangeiras estão ativas
Como padrão do SQLite, execute sempre antes de qualquer operação:
~~~sql
PRAGMA foreign_keys = ON;
~~~

### 2. Rodar na ordem numérica de ficheiros
*   Execute primeiro todos os ficheiros em **`database/ddl/`**, em ordem (01 a 05), sobre uma cópia da base original `hospital.sqlite`.
*   Execute todos os ficheiros em **`database/dml/`**, em ordem (01 a 03), para popular os prédios, turnos e criar a escala de pelo menos 10 médicos.
*   Por fim, utilize os ficheiros na pasta **`database/queries/`** para rodar as validações e emitir relatórios de escala.

---
<p align="center">
  Desenvolvido com ❤️ pela equipe do EscalaMed • 2026
</p>