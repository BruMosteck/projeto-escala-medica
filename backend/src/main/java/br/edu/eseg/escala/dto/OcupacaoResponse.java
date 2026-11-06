package br.edu.eseg.escala.dto;

/** Q6: quantidade de médicos por departamento em um dia da semana. */
public record OcupacaoResponse(
        Long idDepartamento,
        String departamento,
        Integer diaSemana,
        String dia,
        Long medicos) {
}
