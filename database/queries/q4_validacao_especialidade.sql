SELECT m.nome AS medico, d.nome AS departamento, e.nome AS especialidade_medico
FROM alocacao a
JOIN medico m ON a.id_medico = m.id_medico
JOIN especialidade e ON m.id_especialidade = e.id_especialidade
JOIN departamento d ON a.id_departamento = d.id_departamento
LEFT JOIN departamento_especialidade de ON d.id_departamento = de.id_departamento AND m.id_especialidade = de.id_especialidade
WHERE de.id_departamento IS NULL;