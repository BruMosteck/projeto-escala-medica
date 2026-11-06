package br.edu.eseg.escala.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

/**
 * Coração da escala: o médico X está no departamento Y no dia Z, turno W.
 * As restrições abaixo apenas documentam o que o DDL da Parte 1 já garante (ddl-auto=none).
 */
@Entity
@Table(name = "alocacao",
        uniqueConstraints = @UniqueConstraint(columnNames = {"id_medico", "dia_semana", "id_turno"}))
public class Alocacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alocacao")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_medico", nullable = false)
    private Medico medico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_departamento", nullable = false)
    private Departamento departamento;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_turno", nullable = false)
    private Turno turno;

    /** 1 = segunda ... 7 = domingo (ISO 8601). */
    @Column(name = "dia_semana", nullable = false)
    private Integer diaSemana;

    protected Alocacao() {
    }

    public Alocacao(Medico medico, Departamento departamento, Turno turno, Integer diaSemana) {
        this.medico = medico;
        this.departamento = departamento;
        this.turno = turno;
        this.diaSemana = diaSemana;
    }

    public Long getId() {
        return id;
    }

    public Medico getMedico() {
        return medico;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public Turno getTurno() {
        return turno;
    }

    public Integer getDiaSemana() {
        return diaSemana;
    }
}
