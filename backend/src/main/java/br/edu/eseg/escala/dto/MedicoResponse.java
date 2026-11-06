package br.edu.eseg.escala.dto;

import java.time.LocalDate;

import br.edu.eseg.escala.model.Medico;

public record MedicoResponse(
        Long id,
        String nome,
        String crm,
        String email,
        LocalDate dataAdmissao,
        Long idEspecialidade,
        String especialidade,
        String regime,
        Integer cargaHorariaSemanal) {

    public static MedicoResponse de(Medico m) {
        return new MedicoResponse(m.getId(), m.getNome(), m.getCrm(), m.getEmail(), m.getDataAdmissao(),
                m.getEspecialidade().getId(), m.getEspecialidade().getNome(),
                m.getRegime().getDescricao(), m.getRegime().getCargaHorariaSemanal());
    }
}
