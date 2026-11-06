package br.edu.eseg.escala.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.escala.model.Medico;

public interface MedicoRepository extends JpaRepository<Medico, Long> {

    /** Carrega especialidade e regime junto (evita N+1 consultas). Filtro opcional por especialidade. */
    @Query("""
            select m from Medico m
            join fetch m.especialidade e
            join fetch m.regime r
            where (:idEspecialidade is null or e.id = :idEspecialidade)
            order by m.nome
            """)
    List<Medico> listar(@Param("idEspecialidade") Long idEspecialidade);

    @Query("""
            select m from Medico m
            join fetch m.especialidade
            join fetch m.regime
            where m.id = :id
            """)
    Optional<Medico> buscarCompleto(@Param("id") Long id);

    /** Q3: médicos sem nenhuma alocação na semana. */
    @Query("""
            select m from Medico m
            join fetch m.especialidade
            join fetch m.regime
            where not exists (select 1 from Alocacao a where a.medico = m)
            order by m.nome
            """)
    List<Medico> semAlocacao();
}
