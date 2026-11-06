package br.edu.eseg.escala.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.escala.dto.AlocacaoRequest;
import br.edu.eseg.escala.dto.AlocacaoResponse;
import br.edu.eseg.escala.service.AlocacaoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/alocacoes")
public class AlocacaoController {

    private final AlocacaoService service;

    public AlocacaoController(AlocacaoService service) {
        this.service = service;
    }

    /**
     * Pergunta central com filtros opcionais. Exemplos:
     *   GET /api/alocacoes?idMedico=1                    (escala de um médico)
     *   GET /api/alocacoes?idPredio=3&dia=3&idTurno=2    (quem está no Bloco C na quarta à tarde)
     */
    @GetMapping
    public List<AlocacaoResponse> buscar(@RequestParam(required = false) Long idMedico,
                                         @RequestParam(required = false) Long idPredio,
                                         @RequestParam(required = false) Long idDepartamento,
                                         @RequestParam(required = false) Long idTurno,
                                         @RequestParam(required = false) Integer dia) {
        return service.buscar(idMedico, idPredio, idDepartamento, idTurno, dia);
    }

    @GetMapping("/{id}")
    public AlocacaoResponse buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    /** POST /api/alocacoes  com JSON {"idMedico":25,"idDepartamento":1,"idTurno":1,"diaSemana":1} */
    @PostMapping
    public ResponseEntity<AlocacaoResponse> criar(@Valid @RequestBody AlocacaoRequest req) {
        AlocacaoResponse criada = service.criar(req);
        return ResponseEntity.created(URI.create("/api/alocacoes/" + criada.id())).body(criada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }
}
