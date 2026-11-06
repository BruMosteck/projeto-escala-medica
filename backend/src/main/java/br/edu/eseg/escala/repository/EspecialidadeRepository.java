package br.edu.eseg.escala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.escala.model.Especialidade;

public interface EspecialidadeRepository extends JpaRepository<Especialidade, Long> {
}
