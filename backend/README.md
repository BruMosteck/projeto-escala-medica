# Backend: API da Escala Médica

Spring Boot 4.1 · Java 17+ · Spring Data JPA · Hibernate 7 · SQLite

## Como rodar

```bash
./mvnw spring-boot:run       # Windows: mvnw.cmd spring-boot:run
./mvnw test                  # roda os testes (usam uma cópia temporária do banco)
```

Por padrão a aplicação usa `../database/escala.sqlite`. Para apontar outro arquivo:

```bash
ESCALA_DB=/caminho/outro.sqlite ./mvnw spring-boot:run
```

## Arquitetura em camadas

```
controller/   recebe HTTP, valida o JSON (@Valid) e devolve DTOs
service/      regras de negócio (RN01 a RN06) e transações
repository/   interfaces Spring Data JPA com as consultas (JPQL)
model/        entidades JPA mapeando as tabelas da Parte 1
dto/          records de entrada e saída da API
exception/    exceções e tratador global de erros (ProblemDetail)
```

## Configurações importantes (`application.properties`)

| Propriedade | Por quê |
|---|---|
| `jdbc:sqlite:...?foreign_keys=on` | equivale a `PRAGMA foreign_keys = ON` em toda conexão |
| `hibernate.ddl-auto=none` | as tabelas são as da Parte 1; o Hibernate não cria nem altera nada |
| `SQLiteDialect` (hibernate-community-dialects) | o Hibernate não traz dialeto SQLite no núcleo |
| `hikari.maximum-pool-size=1` | o SQLite aceita um escritor por vez |

## Endpoints

| Método | Caminho | Descrição |
|---|---|---|
| GET | `/api/medicos?idEspecialidade=` | lista médicos (filtro opcional) |
| GET | `/api/medicos/{id}` | detalhe do médico |
| GET | `/api/medicos/{id}/escala` | **Q1** escala semanal do médico |
| GET | `/api/alocacoes?idMedico=&idPredio=&idDepartamento=&idTurno=&dia=` | **Q2** busca na escala, filtros opcionais |
| GET | `/api/alocacoes/{id}` | uma alocação |
| POST | `/api/alocacoes` | cria alocação aplicando RN01 a RN06 |
| DELETE | `/api/alocacoes/{id}` | remove alocação |
| GET | `/api/predios`, `/api/departamentos?idPredio=`, `/api/departamentos/{id}` | apoio |
| GET | `/api/turnos`, `/api/especialidades`, `/api/regimes` | apoio |
| GET | `/api/relatorios/medicos-sem-alocacao` | **Q3** |
| GET | `/api/relatorios/alocacoes-incompativeis` | **Q4** (deve ser `[]`) |
| GET | `/api/relatorios/carga` | **Q5** horas alocadas x carga |
| GET | `/api/relatorios/ocupacao` | **Q6** médicos por departamento e dia |

### Exemplo de criação

```http
POST /api/alocacoes
Content-Type: application/json

{"idMedico": 25, "idDepartamento": 1, "idTurno": 1, "diaSemana": 1}
```

Respostas possíveis:

| Status | Quando |
|---|---|
| 201 Created | alocação criada (header `Location` aponta para ela) |
| 400 Bad Request | JSON inválido, por exemplo `diaSemana` fora de 1 a 7 (RN04) |
| 404 Not Found | médico, departamento ou turno inexistente (RN06) |
| 409 Conflict | médico já alocado no mesmo dia e turno (RN01) |
| 422 Unprocessable Content | especialidade incompatível (RN02) ou carga excedida (RN03) |

Exemplo de erro:

```json
{
  "title": "Regra de negócio violada",
  "status": 422,
  "detail": "Carga excedida: Ana Ribeiro tem 30h alocadas, o turno Tarde soma 6h e o regime Parcial 30h permite 30h",
  "regra": "RN03"
}
```
