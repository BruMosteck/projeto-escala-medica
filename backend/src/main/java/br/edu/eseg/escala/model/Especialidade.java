package br.edu.eseg.escala.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Tabela da base fornecida (Parte 1). Somente leitura nesta aplicação. */
@Entity
@Table(name = "especialidade")
public class Especialidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_especialidade")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    protected Especialidade() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
