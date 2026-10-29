-- Q1 | Escala semanal completa de um médico: dia, turno, prédio e departamento
-- Foco: pergunta central. Troque o id_medico para consultar outro médico.
--
-- Resultado esperado: 5 linha(s).
--   medico      | dia     | turno | predio  | departamento
--   ------------+---------+-------+---------+-------------------------
--   Ana Ribeiro | Segunda | Manhã | Bloco B | Ambulatório de Ortopedia
--   Ana Ribeiro | Segunda | Tarde | Bloco B | Ambulatório de Ortopedia
--   Ana Ribeiro | Quarta  | Manhã | Bloco B | Ambulatório de Ortopedia
--   Ana Ribeiro | Quinta  | Tarde | Bloco A | Pronto Atendimento
--   Ana Ribeiro | Sexta   | Manhã | Bloco A | Centro Cirúrgico
SELECT m.nome AS medico,
       CASE a.dia_semana
            WHEN 1 THEN 'Segunda' WHEN 2 THEN 'Terça'  WHEN 3 THEN 'Quarta'
            WHEN 4 THEN 'Quinta'  WHEN 5 THEN 'Sexta'  WHEN 6 THEN 'Sábado'
            WHEN 7 THEN 'Domingo' END AS dia,
       t.nome  AS turno,
       p.nome  AS predio,
       d.nome  AS departamento
FROM alocacao a
JOIN medico m       ON a.id_medico       = m.id_medico
JOIN turno t        ON a.id_turno        = t.id_turno
JOIN departamento d ON a.id_departamento = d.id_departamento
JOIN predio p       ON d.id_predio       = p.id_predio
WHERE a.id_medico = 1
ORDER BY a.dia_semana, t.hora_inicio;
