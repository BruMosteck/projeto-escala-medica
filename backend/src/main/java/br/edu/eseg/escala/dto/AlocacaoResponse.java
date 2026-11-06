package br.edu.eseg.escala.dto;

import br.edu.eseg.escala.model.Alocacao;
import br.edu.eseg.escala.model.DiaSemana;

/** Resposta "achatada": responde direto à pergunta central do projeto. */
public record AlocacaoResponse(
        Long id,
        Long idMedico,
        String medico,
        String especialidade,
        Integer diaSemana,
        String dia,
        Long idTurno,
        String turno,
        String horaInicio,
        String horaFim,
        Long idPredio,
        String predio,
        Long idDepartamento,
        String departamento) {

    public static AlocacaoResponse de(Alocacao a) {
        var m = a.getMedico();
        var t = a.getTurno();
        var d = a.getDepartamento();
        var p = d.getPredio();
        return new AlocacaoResponse(a.getId(),
                m.getId(), m.getNome(), m.getEspecialidade().getNome(),
                a.getDiaSemana(), DiaSemana.de(a.getDiaSemana()).getNome(),
                t.getId(), t.getNome(), t.getHoraInicio(), t.getHoraFim(),
                p.getId(), p.getNome(),
                d.getId(), d.getNome());
    }
}
