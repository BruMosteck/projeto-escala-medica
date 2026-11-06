package br.edu.eseg.escala.dto;

import br.edu.eseg.escala.model.Especialidade;

public record EspecialidadeResponse(Long id, String nome) {

    public static EspecialidadeResponse de(Especialidade e) {
        return new EspecialidadeResponse(e.getId(), e.getNome());
    }
}
