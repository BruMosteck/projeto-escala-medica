package br.edu.eseg.escala.dto;

import br.edu.eseg.escala.model.RegimeTrabalho;

public record RegimeResponse(Long id, String descricao, Integer cargaHorariaSemanal) {

    public static RegimeResponse de(RegimeTrabalho r) {
        return new RegimeResponse(r.getId(), r.getDescricao(), r.getCargaHorariaSemanal());
    }
}
