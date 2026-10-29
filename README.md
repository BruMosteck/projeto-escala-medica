\# Projeto Escala Médica



!\[DER Conceitual](./docs/diagramas/der\_conceitual.png)

> \*Nota: A imagem do diagrama deve ser adicionada à pasta `docs/diagramas/` com o nome `der\_conceitual.png`.\*



\## Resumo

Projeto de modelagem e estruturação de banco de dados focado na criação de uma escala semanal médica. O objetivo é estender um banco de dados SQLite pré-existente para gerenciar os horários e locais de atendimento dos profissionais.



\## Sobre o Projeto

Um hospital fictício opera em vários prédios, cada um com seus respectivos departamentos: UTI, pronto atendimento, ambulatórios e centro cirúrgico. 



Atualmente, a escala dos médicos é montada em planilhas, dificultando a localização exata de cada profissional no dia a dia. Este projeto estrutura o banco de dados para responder de forma rápida à pergunta central: \*\*"Em qual prédio e departamento o médico X está no dia Y, turno Z?"\*\*.



\## Estrutura do Repositório

\* \*\*`database/base/`\*\*: Contém o banco de dados fornecido inicialmente (`hospital.sqlite`).

\* \*\*`database/ddl/`\*\*: Scripts de `CREATE TABLE`, sendo um arquivo para cada tabela, numerados sequencialmente.

\* \*\*`database/dml/`\*\*: Scripts com os `INSERTs` dos prédios, departamentos, turnos e da própria escala.

\* \*\*`database/queries/`\*\*: Contém as consultas SQL obrigatórias, com um arquivo dedicado por consulta.

\* \*\*`docs/diagramas/`\*\*: Arquivos com o DER conceitual e o modelo lógico gerados.



\## Participantes

\* Bruno Simcka Mosteck

\* Miguel Freitas Vieira

\* Henrique Pinheiro Lima

