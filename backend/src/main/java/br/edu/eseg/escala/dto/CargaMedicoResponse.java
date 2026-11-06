package br.edu.eseg.escala.dto;

/** Q5: horas alocadas x carga do regime. */
public record CargaMedicoResponse(
        Long idMedico,
        String medico,
        String regime,
        Integer cargaSemanal,
        Integer horasAlocadas,
        Integer horasDisponiveis,
        boolean excedeu) {
}
