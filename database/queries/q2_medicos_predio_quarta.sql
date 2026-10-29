SELECT m.nome AS medico, d.nome AS departamento
FROM alocacao a
JOIN medico m ON a.id_medico = m.id_medico
JOIN departamento d ON a.id_departamento = d.id_departamento
JOIN predio p ON d.id_predio = p.id_predio
JOIN turno t ON a.id_turno = t.id_turno
WHERE p.id_predio = 1 AND a.dia_semana = 3 AND t.nome = 'Tarde';