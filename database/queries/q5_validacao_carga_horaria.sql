-- Q5 | Horas alocadas x carga do regime por médico, indicando quem excedeu
-- Foco: valida a RN03. Lista todos os médicos (LEFT JOIN: quem não tem
-- alocação aparece com 0 horas).
--
-- Resultado esperado: 36 linhas; nenhum médico com situacao = 'EXCEDEU'.
--   nome             | regime          | horas_alocadas | carga_permitida | situacao
--   -----------------+-----------------+----------------+-----------------+---------
--   Daniel Moreira   | Integral 40h    | 36             | 40              | OK
--   Ana Ribeiro      | Parcial 30h     | 30             | 30              | OK
--   João Pereira     | Integral 40h    | 30             | 40              | OK
--   Renata Guimarães | Integral 40h    | 30             | 40              | OK
--   Camila Nogueira  | Parcial 30h     | 24             | 30              | OK
--   Felipe Teixeira  | Plantonista 24h | 24             | 24              | OK
--   Henrique Batista | Plantonista 24h | 24             | 24              | OK
--   Isabela Fontes   | Plantonista 24h | 24             | 24              | OK
--   Bruno Carvalho   | Parcial 20h     | 18             | 20              | OK
--   Olívia Rezende   | Parcial 20h     | 18             | 20              | OK
--   Rafael Monteiro  | Integral 40h    | 18             | 40              | OK
--   Úrsula Medeiros  | Parcial 20h     | 18             | 20              | OK
--   (+ 24 médicos sem alocação: 0 horas, situacao = OK)
SELECT m.nome,
       r.descricao                        AS regime,
       COALESCE(SUM(t.duracao_horas), 0) AS horas_alocadas,
       r.carga_horaria_semanal            AS carga_permitida,
       CASE WHEN COALESCE(SUM(t.duracao_horas), 0) > r.carga_horaria_semanal
            THEN 'EXCEDEU' ELSE 'OK' END  AS situacao
FROM medico m
JOIN regime_trabalho r ON m.id_regime  = r.id_regime
LEFT JOIN alocacao a   ON a.id_medico  = m.id_medico
LEFT JOIN turno t      ON a.id_turno   = t.id_turno
GROUP BY m.id_medico, m.nome, r.descricao, r.carga_horaria_semanal
ORDER BY horas_alocadas DESC, m.nome;
