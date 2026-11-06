package br.edu.eseg.escala.dto;

import br.edu.eseg.escala.model.Turno;

public record TurnoResponse(Long id, String nome, String horaInicio, String horaFim, Integer duracaoHoras) {

    public static TurnoResponse de(Turno t) {
        return new TurnoResponse(t.getId(), t.getNome(), t.getHoraInicio(), t.getHoraFim(), t.getDuracaoHoras());
    }
}
