package br.edu.eseg.escala.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "turno")
public class Turno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_turno")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    /** Horário em TEXT no formato HH:MM. */
    @Column(name = "hora_inicio", nullable = false)
    private String horaInicio;

    @Column(name = "hora_fim", nullable = false)
    private String horaFim;

    @Column(name = "duracao_horas", nullable = false)
    private Integer duracaoHoras;

    protected Turno() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public String getHoraFim() {
        return horaFim;
    }

    public Integer getDuracaoHoras() {
        return duracaoHoras;
    }
}
