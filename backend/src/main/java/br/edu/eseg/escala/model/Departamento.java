package br.edu.eseg.escala.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "departamento")
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_departamento")
    private Long id;

    @Column(nullable = false)
    private String nome;

    /** RN05: todo departamento pertence a exatamente um prédio. */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_predio", nullable = false)
    private Predio predio;

    /** N:N resolvido pela tabela associativa departamento_especialidade. */
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "departamento_especialidade",
            joinColumns = @JoinColumn(name = "id_departamento"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidade"))
    private Set<Especialidade> especialidades = new HashSet<>();

    protected Departamento() {
    }

    /** RN02: o departamento aceita a especialidade? Compara pelo id. */
    public boolean aceita(Especialidade especialidade) {
        return especialidades.stream().anyMatch(e -> e.getId().equals(especialidade.getId()));
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public Predio getPredio() {
        return predio;
    }

    public Set<Especialidade> getEspecialidades() {
        return especialidades;
    }
}
