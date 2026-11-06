package br.edu.eseg.escala.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.dto.CargaMedicoResponse;
import br.edu.eseg.escala.dto.MedicoResponse;
import br.edu.eseg.escala.dto.OcupacaoResponse;
import br.edu.eseg.escala.model.DiaSemana;
import br.edu.eseg.escala.repository.AlocacaoRepository;
import br.edu.eseg.escala.repository.MedicoRepository;

/** Consultas Q3 a Q6 da Parte 1 expostas como relatórios da API. */
@Service
@Transactional(readOnly = true)
public class RelatorioService {

    private final AlocacaoRepository alocacoes;
    private final MedicoRepository medicos;

    public RelatorioService(AlocacaoRepository alocacoes, MedicoRepository medicos) {
        this.alocacoes = alocacoes;
        this.medicos = medicos;
    }

    /** Q3 */
    public List<MedicoResponse> medicosSemAlocacao() {
        return medicos.semAlocacao().stream().map(MedicoResponse::de).toList();
    }

    /** Q4: deve retornar lista vazia se a RN02 foi respeitada. */
    public List<AlocacaoResponse> alocacoesIncompativeis() {
        return alocacoes.incompativeis().stream().map(AlocacaoResponse::de).toList();
    }

    /** Q5 */
    public List<CargaMedicoResponse> carga() {
        return alocacoes.cargaPorMedico().stream().map(l -> {
            int carga = ((Number) l[3]).intValue();
            int horas = ((Number) l[4]).intValue();
            return new CargaMedicoResponse(((Number) l[0]).longValue(), (String) l[1], (String) l[2],
                    carga, horas, Math.max(carga - horas, 0), horas > carga);
        }).toList();
    }

    /** Q6 */
    public List<OcupacaoResponse> ocupacao() {
        return alocacoes.ocupacaoPorDepartamentoEDia().stream().map(l -> {
            int dia = ((Number) l[2]).intValue();
            return new OcupacaoResponse(((Number) l[0]).longValue(), (String) l[1], dia,
                    DiaSemana.de(dia).getNome(), ((Number) l[3]).longValue());
        }).toList();
    }
}
