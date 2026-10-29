-- Q2 | Médicos presentes em um prédio na quarta-feira à tarde
-- Foco: pergunta central. Troque o nome do prédio para consultar outro.
--
-- Resultado esperado: 4 linha(s).
--   medico          | especialidade      | departamento
--   ----------------+--------------------+-------------------
--   Bruno Carvalho  | Neurologia         | Pronto Atendimento
--   Úrsula Medeiros | Clínica Médica     | Pronto Atendimento
--   Camila Nogueira | Cardiologia        | UTI
--   João Pereira    | Medicina Intensiva | UTI
SELECT m.nome AS medico,
       e.nome AS especialidade,
       d.nome AS departamento
FROM alocacao a
JOIN medico m        ON a.id_medico        = m.id_medico
JOIN especialidade e ON m.id_especialidade = e.id_especialidade
JOIN departamento d  ON a.id_departamento  = d.id_departamento
JOIN predio p        ON d.id_predio        = p.id_predio
JOIN turno t         ON a.id_turno         = t.id_turno
WHERE p.nome = 'Bloco A'
  AND a.dia_semana = 3          -- quarta-feira
  AND t.nome = 'Tarde'
ORDER BY d.nome, m.nome;
