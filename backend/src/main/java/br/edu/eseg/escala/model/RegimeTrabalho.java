package br.edu.eseg.escala.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Tabela da base fornecida (Parte 1). Somente leitura nesta aplicação. */
@Entity
@Table(name = "regime_trabalho")
public class RegimeTrabalho {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_regime")
    private Long id;

    @Column(nullable = false, unique = true)
    private String descricao;

    @Column(name = "carga_horaria_semanal", nullable = false)
    private Integer cargaHorariaSemanal;

    protected RegimeTrabalho() {
    }

    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public Integer getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }
}
