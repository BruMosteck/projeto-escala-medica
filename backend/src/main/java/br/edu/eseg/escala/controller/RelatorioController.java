package br.edu.eseg.escala.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.dto.CargaMedicoResponse;
import br.edu.eseg.escala.dto.MedicoResponse;
import br.edu.eseg.escala.dto.OcupacaoResponse;
import br.edu.eseg.escala.service.RelatorioService;

/** Consultas de auditoria da Parte 1 (Q3 a Q6). */
@RestController
@RequestMapping("/api/relatorios")
public class RelatorioController {

    private final RelatorioService service;

    public RelatorioController(RelatorioService service) {
        this.service = service;
    }

    /** Q3 */
    @GetMapping("/medicos-sem-alocacao")
    public List<MedicoResponse> medicosSemAlocacao() {
        return service.medicosSemAlocacao();
    }

    /** Q4: deve retornar [] */
    @GetMapping("/alocacoes-incompativeis")
    public List<AlocacaoResponse> alocacoesIncompativeis() {
        return service.alocacoesIncompativeis();
    }

    /** Q5 */
    @GetMapping("/carga")
    public List<CargaMedicoResponse> carga() {
        return service.carga();
    }

    /** Q6 */
    @GetMapping("/ocupacao")
    public List<OcupacaoResponse> ocupacao() {
        return service.ocupacao();
    }
}
