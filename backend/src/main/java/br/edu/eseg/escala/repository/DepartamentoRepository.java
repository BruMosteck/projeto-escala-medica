package br.edu.eseg.escala.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.edu.eseg.escala.model.Departamento;

public interface DepartamentoRepository extends JpaRepository<Departamento, Long> {

    @Query("""
            select distinct d from Departamento d
            join fetch d.predio p
            left join fetch d.especialidades
            where (:idPredio is null or p.id = :idPredio)
            order by p.nome, d.nome
            """)
    List<Departamento> listar(@Param("idPredio") Long idPredio);

    @Query("""
            select d from Departamento d
            join fetch d.predio
            left join fetch d.especialidades
            where d.id = :id
            """)
    Optional<Departamento> buscarCompleto(@Param("id") Long id);
}
