<p align="center">
  <img src="docs/logo.png" alt="EscalaMed Banner" width="100%"/>
</p>

# 🏥 EscalaMed - Sistema de Escala Médica

![SQLite](https://img.shields.io/badge/SQLite-07405E?style=for-the-badge&logo=sqlite&logoColor=white)
![VSCodium](https://img.shields.io/badge/VSCodium-2C2C32?style=for-the-badge&logo=vscodium&logoColor=229CE6)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)

## 📱 Sobre o Projeto

O **EscalaMed** é um sistema projetado para solucionar um problema crítico de gestão hospitalar: a descentralização e a falta de visibilidade na escala de médicos de um grande hospital. Atualmente, a escala é feita em planilhas dispersas, impossibilitando localizar os médicos de prontidão de forma ágil. 

Com base num cadastro pré-existente de médicos em SQLite, o projeto estende a base de dados para modelar e automatizar a escala semanal de múltiplos prédios e departamentos (UTI, Ambulatórios, Centro Cirúrgico, Pronto Atendimento), integrando restrições de carga horária e regras de compatibilidade de especialidades.

**🎯 Pergunta Central**  
*Em qual prédio e departamento o médico X está no dia Y, turno Z?*

---

## 🏗️ Estrutura do Projeto

O repositório está organizado de forma modular nas seguintes pastas:

* **`database/base/`**: Scripts iniciais de infraestrutura (Especialidade, Regime de Trabalho e Médico).
* **`database/ddl/`**: Scripts de criação da modelagem atualizada (Prédio, Departamento, Turno, etc.).
* **`database/dml/`**: Scripts de inserção de dados para povoamento inicial do banco.
* **`database/queries/`**: Consultas SQL para teste e validação das regras operacionais.
* **`docs/diagramas/`**: Modelos visuais (Conceitual e Lógico) da arquitetura do banco.

## 📊 Modelagem de Dados

A arquitetura do banco de dados foi expandida para incluir um controle físico e temporal mais detalhado:
* **Prédio & Departamento:** Com mapeamento específico de `endereco` e `andar`.
* **Turno:** Validação rigorosa de horários no formato 24h (`HH:MM`) utilizando a função `GLOB` e cálculo de `duracao_horas` cobrindo viradas de noite.
* **Alocação:** Mapeamento central da escala por `dia_semana` (1 a 7) e travas lógicas contra conflitos.

### Modelo Conceitual
<details>
  <summary><b>➕ Clique aqui para expandir / ➖ retrair o diagrama</b></summary>
  <br>
  <img src="docs/diagramas/01_der_conceitual.png" alt="DER Conceitual" width="100%">
</details>

<br>

### Modelo Lógico
<details>
  <summary><b>➕ Clique aqui para expandir / ➖ retrair o diagrama</b></summary>
  <br>
  <img src="docs/diagramas/02_modelo_logico.png" alt="Modelo Lógico" width="100%">
</details>

## ⚙️ Regras de Negócio e Restrições (Constraints)

| Regra | Descrição | Implementação SQL |
| :--- | :--- | :--- |
| **RN01** | Prevenção de conflito de horários (mesmo médico/dia/turno). | `UNIQUE (id_medico, dia_semana, id_turno)` |
| **RN02** | Especialidade compatível com o departamento alocado. | Validado via `queries/q4` |
| **RN03** | Respeito à carga horária máxima semanal do médico. | Validado via `queries/q5` |
| **RN04** | Dia da semana válido (1 = Segunda a 7 = Domingo). | `CHECK (dia_semana BETWEEN 1 AND 7)` |
| **RN05** | Formatação estrita de horários (00:00 a 23:59). | `CHECK (hora_inicio GLOB '[0-2][0-9]:[0-5][0-9]')` |

## 🚀 Como Executar

1. Abra a raiz do projeto no **VSCodium**.
2. Certifique-se de ter a extensão **SQLite (alexcvzz)** instalada para execução de scripts.
3. Navegue até a pasta `database/ddl/` e execute os scripts de `01` a `05` utilizando o comando `SQLite: Run Query`.
4. Execute os scripts da pasta `database/dml/` para popular as tabelas com os dados de teste.
5. Utilize a extensão **SQLite Viewer** para inspecionar os dados inseridos visualmente.
6. Rode os scripts da pasta `database/queries/` para validar a saída das escalas.

---

## 👨‍💻 Equipe de Desenvolvimento

Esta solução foi modelada e implementada pelos seguintes engenheiros:

* 👤 **Bruno Simcka Mosteck** — [GitHub](https://github.com/BruMosteck)
* 👤 **Miguel Freitas Vieira** — [GitHub](https://github.com/MiguelVFreitas)
* 👤 **Henrique Pinheiro Lima** — [GitHub](https://github.com/PinheiroLim)

**Disciplina:** Banco de Dados | **Professor:** Stelvio Barboza