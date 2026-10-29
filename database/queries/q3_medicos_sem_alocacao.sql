SELECT m.nome
FROM medico m
LEFT JOIN alocacao a ON m.id_medico = a.id_medico
WHERE a.id_alocacao IS NULL;