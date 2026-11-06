package br.edu.eseg.escala.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/** Corpo do POST /api/alocacoes. As anotações são validadas antes de chegar ao serviço. */
public record AlocacaoRequest(
        @NotNull(message = "é obrigatório") Long idMedico,
        @NotNull(message = "é obrigatório") Long idDepartamento,
        @NotNull(message = "é obrigatório") Long idTurno,
        @NotNull(message = "é obrigatório")
        @Min(value = 1, message = "deve ser de 1 (segunda) a 7 (domingo)")
        @Max(value = 7, message = "deve ser de 1 (segunda) a 7 (domingo)")
        Integer diaSemana) {
}
