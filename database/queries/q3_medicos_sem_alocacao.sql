-- Q3 | Médicos sem nenhuma alocação na semana
-- Foco: LEFT JOIN (médicos sem correspondência em alocacao ficam com NULL).
--
-- Resultado esperado: 24 linha(s).
--   id_medico | nome               | especialidade
--   ----------+--------------------+--------------------------
--   25        | Beatriz Falcão     | Clínica Médica
--   26        | Caio Dantas        | Psiquiatria
--   27        | Débora Leme        | Radiologia
--   5         | Eduarda Pacheco    | Dermatologia
--   7         | Gabriela Lacerda   | Dermatologia
--   28        | Gustavo Araújo     | Anestesiologia
--   29        | Helena Tavares     | Cardiologia
--   30        | Igor Sampaio       | Anestesiologia
--   31        | Juliana Brandão    | Cardiologia
--   11        | Karina Siqueira    | Dermatologia
--   12        | Leonardo Amaral    | Ortopedia
--   32        | Lucas Mendonça     | Neurologia
--   13        | Mariana Coutinho   | Ortopedia
--   33        | Natália Freitas    | Ginecologia e Obstetrícia
--   14        | Nicolas Barreto    | Radiologia
--   34        | Otávio Queiroz     | Ginecologia e Obstetrícia
--   16        | Paulo Viana        | Cirurgia Geral
--   35        | Priscila Valente   | Clínica Médica
--   24        | Rodrigo Peixoto    | Cirurgia Geral
--   19        | Sofia Cardoso      | Pediatria
--   36        | Sérgio Castilho    | Pediatria
--   20        | Thiago Bastos      | Medicina Intensiva
--   22        | Vinícius Prado     | Psiquiatria
--   23        | Yasmin Albuquerque | Neurologia
SELECT m.id_medico,
       m.nome,
       e.nome AS especialidade
FROM medico m
JOIN especialidade e ON m.id_especialidade = e.id_especialidade
LEFT JOIN alocacao a ON a.id_medico = m.id_medico
WHERE a.id_alocacao IS NULL
ORDER BY m.nome;
