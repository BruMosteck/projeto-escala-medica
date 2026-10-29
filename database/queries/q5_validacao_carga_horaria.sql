SELECT m.nome, SUM(t.duracao_horas) AS horas_alocadas, r.carga_horaria AS carga_permitida
FROM alocacao a
JOIN medico m ON a.id_medico = m.id_medico
JOIN turno t ON a.id_turno = t.id_turno
JOIN regime_trabalho r ON m.id_regime_trabalho = r.id_regime_trabalho
GROUP BY m.id_medico, r.carga_horaria
HAVING SUM(t.duracao_horas) > r.carga_horaria;