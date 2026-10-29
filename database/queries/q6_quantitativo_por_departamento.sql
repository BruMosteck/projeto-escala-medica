SELECT d.nome AS departamento, a.dia_semana, COUNT(DISTINCT a.id_medico) AS qtd_medicos
FROM alocacao a
JOIN departamento d ON a.id_departamento = d.id_departamento
GROUP BY d.id_departamento, a.dia_semana
ORDER BY a.dia_semana, d.nome;