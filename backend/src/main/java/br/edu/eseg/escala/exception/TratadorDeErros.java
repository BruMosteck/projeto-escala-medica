package br.edu.eseg.escala.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/** Converte exceções em respostas JSON padronizadas (RFC 9457, ProblemDetail). */
@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ProblemDetail naoEncontrado(RecursoNaoEncontradoException e) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        p.setTitle("Recurso não encontrado");
        return p;
    }

    @ExceptionHandler(RegraNegocioException.class)
    public ProblemDetail regraNegocio(RegraNegocioException e) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(e.getStatus(), e.getMessage());
        p.setTitle("Regra de negócio violada");
        p.setProperty("regra", e.getRegra());
        return p;
    }

    /** Erros de @Valid no corpo da requisição (ex.: diaSemana = 8). */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail validacao(MethodArgumentNotValidException e) {
        Map<String, String> campos = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors()
                .forEach(f -> campos.put(f.getField(), f.getDefaultMessage()));
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Dados inválidos");
        p.setTitle("Erro de validação");
        p.setProperty("campos", campos);
        return p;
    }

    /** Última linha de defesa: constraint do banco (UNIQUE, CHECK, FK) recusou a operação. */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ProblemDetail integridade(DataIntegrityViolationException e) {
        ProblemDetail p = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT,
                "O banco de dados recusou a operação: " + e.getMostSpecificCause().getMessage());
        p.setTitle("Violação de integridade");
        return p;
    }
}
