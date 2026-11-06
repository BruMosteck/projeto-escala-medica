package br.edu.eseg.escala.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.escala.model.Alocacao;

public interface AlocacaoRepository extends JpaRepository<Alocacao, Long> {

    /** RN01: o médico já está alocado nesse dia e turno? (consulta derivada do nome do método) */
    boolean existsByMedico_IdAndDiaSemanaAndTurno_Id(Long idMedico, Integer diaSemana, Long idTurno);

    /** RN03: total de horas já alocadas para o médico na semana. */
    @Query("""
            select coalesce(sum(t.duracaoHoras), 0)
            from Alocacao a join a.turno t
            where a.medico.id = :idMedico
            """)
    Long somarHorasDoMedico(@Param("idMedico") Long idMedico);

    /**
     * Busca da escala com filtros opcionais (parâmetro nulo = sem filtro).
     * Cobre Q1 (por médico) e Q2 (por prédio, dia e turno).
     */
    @Query("""
            select a from Alocacao a
            join fetch a.medico m
            join fetch m.especialidade
            join fetch a.departamento d
            join fetch d.predio p
            join fetch a.turno t
            where (:idMedico is null or m.id = :idMedico)
              and (:idPredio is null or p.id = :idPredio)
              and (:idDepartamento is null or d.id = :idDepartamento)
              and (:idTurno is null or t.id = :idTurno)
              and (:dia is null or a.diaSemana = :dia)
            order by a.diaSemana, t.horaInicio, p.nome, d.nome, m.nome
            """)
    List<Alocacao> buscar(@Param("idMedico") Long idMedico,
                          @Param("idPredio") Long idPredio,
                          @Param("idDepartamento") Long idDepartamento,
                          @Param("idTurno") Long idTurno,
                          @Param("dia") Integer dia);

    /** Q4: alocações em departamento que não aceita a especialidade do médico (deve vir vazio). */
    @Query("""
            select a from Alocacao a
            join fetch a.medico m
            join fetch m.especialidade e
            join fetch a.departamento d
            join fetch d.predio
            join fetch a.turno
            where e not member of d.especialidades
            """)
    List<Alocacao> incompativeis();

    /** Q5: horas alocadas x carga do regime, para todos os médicos (left join inclui quem tem 0h). */
    @Query("""
            select m.id, m.nome, r.descricao, r.cargaHorariaSemanal, coalesce(sum(t.duracaoHoras), 0)
            from Medico m
            join m.regime r
            left join Alocacao a on a.medico = m
            left join a.turno t
            group by m.id, m.nome, r.descricao, r.cargaHorariaSemanal
            order by m.nome
            """)
    List<Object[]> cargaPorMedico();

    /** Q6: quantidade de médicos por departamento em cada dia da semana. */
    @Query("""
            select d.id, d.nome, a.diaSemana, count(distinct a.medico.id)
            from Alocacao a join a.departamento d
            group by d.id, d.nome, a.diaSemana
            order by d.nome, a.diaSemana
            """)
    List<Object[]> ocupacaoPorDepartamentoEDia();
}
