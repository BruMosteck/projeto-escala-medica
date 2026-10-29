-- Q6 | Quantidade de médicos por departamento em cada dia da semana
-- Foco: GROUP BY. COUNT(DISTINCT) conta o médico uma vez por dia, mesmo
-- que ele tenha mais de um turno no mesmo departamento.
--
-- Resultado esperado: 29 linha(s).
--   predio  | departamento               | dia_semana | qtd_medicos
--   --------+----------------------------+------------+------------
--   Bloco A | Centro Cirúrgico           | 2          | 1
--   Bloco A | Centro Cirúrgico           | 5          | 2
--   Bloco A | Centro Cirúrgico           | 6          | 1
--   Bloco A | Pronto Atendimento         | 3          | 2
--   Bloco A | Pronto Atendimento         | 4          | 2
--   Bloco A | Pronto Atendimento         | 6          | 1
--   Bloco A | UTI                        | 1          | 2
--   Bloco A | UTI                        | 2          | 1
--   Bloco A | UTI                        | 3          | 3
--   Bloco A | UTI                        | 4          | 1
--   Bloco A | UTI                        | 5          | 2
--   Bloco B | Ambulatório de Cardiologia | 1          | 1
--   Bloco B | Ambulatório de Cardiologia | 4          | 1
--   Bloco B | Ambulatório de Neurologia  | 1          | 1
--   Bloco B | Ambulatório de Neurologia  | 2          | 1
--   Bloco B | Ambulatório de Neurologia  | 3          | 1
--   Bloco B | Ambulatório de Ortopedia   | 1          | 1
--   Bloco B | Ambulatório de Ortopedia   | 3          | 1
--   Bloco C | Diagnóstico por Imagem     | 1          | 1
--   Bloco C | Diagnóstico por Imagem     | 2          | 1
--   Bloco C | Diagnóstico por Imagem     | 3          | 1
--   Bloco C | Diagnóstico por Imagem     | 4          | 1
--   Bloco C | Diagnóstico por Imagem     | 5          | 1
--   Bloco C | Maternidade                | 2          | 1
--   Bloco C | Maternidade                | 4          | 1
--   Bloco C | Maternidade                | 7          | 1
--   Bloco C | Pediatria                  | 1          | 1
--   Bloco C | Pediatria                  | 3          | 1
--   Bloco C | Pediatria                  | 5          | 1
SELECT p.nome AS predio,
       d.nome AS departamento,
       a.dia_semana,
       COUNT(DISTINCT a.id_medico) AS qtd_medicos
FROM alocacao a
JOIN departamento d ON a.id_departamento = d.id_departamento
JOIN predio p       ON d.id_predio       = p.id_predio
GROUP BY p.nome, d.id_departamento, d.nome, a.dia_semana
ORDER BY p.nome, d.nome, a.dia_semana;
