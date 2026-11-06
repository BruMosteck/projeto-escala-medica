package br.edu.eseg.escala.dto;

import java.util.List;

import br.edu.eseg.escala.model.Departamento;
import br.edu.eseg.escala.model.Especialidade;

public record DepartamentoResponse(
        Long id,
        String nome,
        Long idPredio,
        String predio,
        List<String> especialidadesAceitas) {

    public static DepartamentoResponse de(Departamento d) {
        List<String> especialidades = d.getEspecialidades().stream()
                .map(Especialidade::getNome)
                .sorted()
                .toList();
        return new DepartamentoResponse(d.getId(), d.getNome(), d.getPredio().getId(), d.getPredio().getNome(),
                especialidades);
    }
}
