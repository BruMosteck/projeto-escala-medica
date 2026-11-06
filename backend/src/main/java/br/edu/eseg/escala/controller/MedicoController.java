package br.edu.eseg.escala.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.dto.MedicoResponse;
import br.edu.eseg.escala.service.MedicoService;

@RestController
@RequestMapping("/api/medicos")
public class MedicoController {

    private final MedicoService service;

    public MedicoController(MedicoService service) {
        this.service = service;
    }

    /** GET /api/medicos  ou  /api/medicos?idEspecialidade=4 */
    @GetMapping
    public List<MedicoResponse> listar(@RequestParam(required = false) Long idEspecialidade) {
        return service.listar(idEspecialidade);
    }

    @GetMapping("/{id}")
    public MedicoResponse buscar(@PathVariable Long id) {
        return service.buscar(id);
    }

    /** Q1: GET /api/medicos/1/escala */
    @GetMapping("/{id}/escala")
    public List<AlocacaoResponse> escala(@PathVariable Long id) {
        return service.escala(id);
    }
}
