SELECT a.dia, t.nome AS turno, p.nome AS predio, d.nome AS departamento
FROM alocacao a
JOIN turno t ON a.id_turno = t.id_turno
JOIN departamento d ON a.id_departamento = d.id_departamento
JOIN predio p ON d.id_predio = p.id_predio
WHERE a.id_medico = 1;