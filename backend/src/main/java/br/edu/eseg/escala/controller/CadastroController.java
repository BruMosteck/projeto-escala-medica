package br.edu.eseg.escala.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.eseg.escala.dto.DepartamentoResponse;
import br.edu.eseg.escala.dto.EspecialidadeResponse;
import br.edu.eseg.escala.dto.PredioResponse;
import br.edu.eseg.escala.dto.RegimeResponse;
import br.edu.eseg.escala.dto.TurnoResponse;
import br.edu.eseg.escala.service.CadastroService;

@RestController
@RequestMapping("/api")
public class CadastroController {

    private final CadastroService service;

    public CadastroController(CadastroService service) {
        this.service = service;
    }

    @GetMapping("/predios")
    public List<PredioResponse> predios() {
        return service.predios();
    }

    /** GET /api/departamentos  ou  /api/departamentos?idPredio=2 */
    @GetMapping("/departamentos")
    public List<DepartamentoResponse> departamentos(@RequestParam(required = false) Long idPredio) {
        return service.departamentos(idPredio);
    }

    @GetMapping("/departamentos/{id}")
    public DepartamentoResponse departamento(@PathVariable Long id) {
        return service.departamento(id);
    }

    @GetMapping("/turnos")
    public List<TurnoResponse> turnos() {
        return service.turnos();
    }

    @GetMapping("/especialidades")
    public List<EspecialidadeResponse> especialidades() {
        return service.especialidades();
    }

    @GetMapping("/regimes")
    public List<RegimeResponse> regimes() {
        return service.regimes();
    }
}
