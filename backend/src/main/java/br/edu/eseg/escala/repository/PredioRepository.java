package br.edu.eseg.escala.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.eseg.escala.model.Predio;

public interface PredioRepository extends JpaRepository<Predio, Long> {
}
