package br.edu.eseg.escala.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.eseg.escala.dto.AlocacaoRequest;
import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.exception.RecursoNaoEncontradoException;
import br.edu.eseg.escala.exception.RegraNegocioException;
import br.edu.eseg.escala.model.Alocacao;
import br.edu.eseg.escala.model.Departamento;
import br.edu.eseg.escala.model.DiaSemana;
import br.edu.eseg.escala.model.Medico;
import br.edu.eseg.escala.model.Turno;
import br.edu.eseg.escala.repository.AlocacaoRepository;
import br.edu.eseg.escala.repository.DepartamentoRepository;
import br.edu.eseg.escala.repository.MedicoRepository;
import br.edu.eseg.escala.repository.TurnoRepository;

/**
 * Camada de serviço: aqui ficam as regras que o banco não consegue garantir sozinho (RN02 e RN03).
 * RN01, RN04 e RN06 também são verificadas antes do INSERT para devolver mensagens claras;
 * as constraints do banco continuam valendo como última linha de defesa.
 */
@Service
public class AlocacaoService {

    private final AlocacaoRepository alocacoes;
    private final MedicoRepository medicos;
    private final DepartamentoRepository departamentos;
    private final TurnoRepository turnos;

    public AlocacaoService(AlocacaoRepository alocacoes, MedicoRepository medicos,
                           DepartamentoRepository departamentos, TurnoRepository turnos) {
        this.alocacoes = alocacoes;
        this.medicos = medicos;
        this.departamentos = departamentos;
        this.turnos = turnos;
    }

    @Transactional(readOnly = true)
    public List<AlocacaoResponse> buscar(Long idMedico, Long idPredio, Long idDepartamento, Long idTurno,
                                         Integer dia) {
        return alocacoes.buscar(idMedico, idPredio, idDepartamento, idTurno, dia).stream()
                .map(AlocacaoResponse::de)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlocacaoResponse buscarPorId(Long id) {
        return alocacoes.findById(id)
                .map(AlocacaoResponse::de)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Alocação", id));
    }

    @Transactional
    public AlocacaoResponse criar(AlocacaoRequest req) {
        // RN06: integridade referencial (médico, departamento e turno precisam existir)
        Medico medico = medicos.buscarCompleto(req.idMedico())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Médico", req.idMedico()));
        Departamento departamento = departamentos.buscarCompleto(req.idDepartamento())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Departamento", req.idDepartamento()));
        Turno turno = turnos.findById(req.idTurno())
                .orElseThrow(() -> new RecursoNaoEncontradoException("Turno", req.idTurno()));

        // RN04: dia da semana válido (também garantido por @Min/@Max e pelo CHECK do banco)
        String dia = DiaSemana.de(req.diaSemana()).getNome();

        // RN01: sem conflito de horário (também garantido pelo UNIQUE do banco)
        if (alocacoes.existsByMedico_IdAndDiaSemanaAndTurno_Id(medico.getId(), req.diaSemana(), turno.getId())) {
            throw new RegraNegocioException("RN01", HttpStatus.CONFLICT,
                    medico.getNome() + " já está alocado(a) em " + dia + ", turno " + turno.getNome());
        }

        // RN02: o departamento precisa aceitar a especialidade do médico
        if (!departamento.aceita(medico.getEspecialidade())) {
            throw new RegraNegocioException("RN02", HttpStatus.UNPROCESSABLE_CONTENT,
                    departamento.getNome() + " não atende a especialidade "
                            + medico.getEspecialidade().getNome());
        }

        // RN03: a soma das horas não pode passar da carga semanal do regime
        int horasAtuais = alocacoes.somarHorasDoMedico(medico.getId()).intValue();
        int carga = medico.getRegime().getCargaHorariaSemanal();
        if (horasAtuais + turno.getDuracaoHoras() > carga) {
            throw new RegraNegocioException("RN03", HttpStatus.UNPROCESSABLE_CONTENT,
                    "Carga excedida: " + medico.getNome() + " tem " + horasAtuais + "h alocadas, o turno "
                            + turno.getNome() + " soma " + turno.getDuracaoHoras() + "h e o regime "
                            + medico.getRegime().getDescricao() + " permite " + carga + "h");
        }

        Alocacao nova = alocacoes.save(new Alocacao(medico, departamento, turno, req.diaSemana()));
        return AlocacaoResponse.de(nova);
    }

    @Transactional
    public void remover(Long id) {
        if (!alocacoes.existsById(id)) {
            throw new RecursoNaoEncontradoException("Alocação", id);
        }
        alocacoes.deleteById(id);
    }
}
