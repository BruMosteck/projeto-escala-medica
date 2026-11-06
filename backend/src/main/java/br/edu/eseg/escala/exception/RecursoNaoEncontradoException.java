package br.edu.eseg.escala.exception;

/** Gera HTTP 404. */
public class RecursoNaoEncontradoException extends RuntimeException {

    public RecursoNaoEncontradoException(String recurso, Long id) {
        super(recurso + " " + id + " não encontrado(a)");
    }
}
