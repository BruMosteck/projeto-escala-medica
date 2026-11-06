package br.edu.eseg.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.dto.MedicoResponse;
import br.edu.eseg.escala.exception.RecursoNaoEncontradoException;
import br.edu.eseg.escala.repository.AlocacaoRepository;
import br.edu.eseg.escala.repository.MedicoRepository;

@Service
@Transactional(readOnly = true)
public class MedicoService {

    private final MedicoRepository medicos;
    private final AlocacaoRepository alocacoes;

    public MedicoService(MedicoRepository medicos, AlocacaoRepository alocacoes) {
        this.medicos = medicos;
        this.alocacoes = alocacoes;
    }

    public List<MedicoResponse> listar(Long idEspecialidade) {
        return medicos.listar(idEspecialidade).stream().map(MedicoResponse::de).toList();
    }

    public MedicoResponse buscar(Long id) {
        return medicos.buscarCompleto(id)
                .map(MedicoResponse::de)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico", id));
    }

    /** Q1: escala semanal completa de um médico. */
    public List<AlocacaoResponse> escala(Long id) {
        if (!medicos.existsById(id)) {
            throw new RecursoNaoEncontradoException("Médico", id);
        }
        return alocacoes.buscar(id, null, null, null, null).stream().map(AlocacaoResponse::de).toList();
    }
}
