package br.edu.eseg.escala.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "predio")
public class Predio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_predio")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    private String endereco;

    protected Predio() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }
}
