package br.edu.eseg.escala.dto;

import br.edu.eseg.escala.model.Predio;

public record PredioResponse(Long id, String nome, String endereco) {

    public static PredioResponse de(Predio p) {
        return new PredioResponse(p.getId(), p.getNome(), p.getEndereco());
    }
}
