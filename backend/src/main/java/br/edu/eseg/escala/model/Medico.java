package br.edu.eseg.escala.model;

import java.time.LocalDate;

import br.edu.eseg.escala.model.converter.LocalDateTextoConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/** Tabela da base fornecida (Parte 1). Somente leitura nesta aplicação. */
@Entity
@Table(name = "medico")
public class Medico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medico")
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String crm;

    @Column(nullable = false, unique = true)
    private String email;

    /** No SQLite a data está gravada como TEXT 'AAAA-MM-DD'; o converter faz a ponte com LocalDate. */
    @Convert(converter = LocalDateTextoConverter.class)
    @Column(name = "data_admissao", nullable = false)
    private LocalDate dataAdmissao;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_especialidade", nullable = false)
    private Especialidade especialidade;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_regime", nullable = false)
    private RegimeTrabalho regime;

    protected Medico() {
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDataAdmissao() {
        return dataAdmissao;
    }

    public Especialidade getEspecialidade() {
        return especialidade;
    }

    public RegimeTrabalho getRegime() {
        return regime;
    }
}
