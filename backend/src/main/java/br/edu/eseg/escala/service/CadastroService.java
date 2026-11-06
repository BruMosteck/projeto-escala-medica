package br.edu.eseg.escala.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.escala.dto.DepartamentoResponse;
import br.edu.eseg.escala.dto.EspecialidadeResponse;
import br.edu.eseg.escala.dto.PredioResponse;
import br.edu.eseg.escala.dto.RegimeResponse;
import br.edu.eseg.escala.dto.TurnoResponse;
import br.edu.eseg.escala.exception.RecursoNaoEncontradoException;
import br.edu.eseg.escala.repository.DepartamentoRepository;
import br.edu.eseg.escala.repository.EspecialidadeRepository;
import br.edu.eseg.escala.repository.PredioRepository;
import br.edu.eseg.escala.repository.RegimeTrabalhoRepository;
import br.edu.eseg.escala.repository.TurnoRepository;

/** Consultas das tabelas de apoio (prédios, departamentos, turnos, especialidades e regimes). */
@Service
@Transactional(readOnly = true)
public class CadastroService {

    private final PredioRepository predios;
    private final DepartamentoRepository departamentos;
    private final TurnoRepository turnos;
    private final EspecialidadeRepository especialidades;
    private final RegimeTrabalhoRepository regimes;

    public CadastroService(PredioRepository predios, DepartamentoRepository departamentos, TurnoRepository turnos,
                           EspecialidadeRepository especialidades, RegimeTrabalhoRepository regimes) {
        this.predios = predios;
        this.departamentos = departamentos;
        this.turnos = turnos;
        this.especialidades = especialidades;
        this.regimes = regimes;
    }

    public List<PredioResponse> predios() {
        return predios.findAll(Sort.by("nome")).stream().map(PredioResponse::de).toList();
    }

    public List<DepartamentoResponse> departamentos(Long idPredio) {
        return departamentos.listar(idPredio).stream().map(DepartamentoResponse::de).toList();
    }

    public DepartamentoResponse departamento(Long id) {
        return departamentos.buscarCompleto(id)
                .map(DepartamentoResponse::de)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Departamento", id));
    }

    public List<TurnoResponse> turnos() {
        return turnos.findAll(Sort.by("horaInicio")).stream().map(TurnoResponse::de).toList();
    }

    public List<EspecialidadeResponse> especialidades() {
        return especialidades.findAll(Sort.by("nome")).stream().map(EspecialidadeResponse::de).toList();
    }

    public List<RegimeResponse> regimes() {
        return regimes.findAll(Sort.by("cargaHorariaSemanal")).stream().map(RegimeResponse::de).toList();
    }
}
