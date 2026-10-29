-- Q4 | Alocações em departamento incompatível com a especialidade do médico
-- Foco: valida a RN02. DEVE RETORNAR VAZIO.
--
-- Resultado esperado: 0 linhas (toda alocação respeita a RN02).
--   (nenhuma linha)
SELECT m.nome AS medico,
       e.nome AS especialidade_medico,
       d.nome AS departamento,
       a.dia_semana,
       t.nome AS turno
FROM alocacao a
JOIN medico m        ON a.id_medico        = m.id_medico
JOIN especialidade e ON m.id_especialidade = e.id_especialidade
JOIN departamento d  ON a.id_departamento  = d.id_departamento
JOIN turno t         ON a.id_turno         = t.id_turno
LEFT JOIN departamento_especialidade de
       ON de.id_departamento  = a.id_departamento
      AND de.id_especialidade = m.id_especialidade
WHERE de.id_departamento IS NULL
ORDER BY m.nome, a.dia_semana;
