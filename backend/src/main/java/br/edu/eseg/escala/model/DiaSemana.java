package br.edu.eseg.escala.model;

/** Nomes dos dias para exibição. No banco o dia é um INTEGER de 1 a 7 (ISO 8601). */
public enum DiaSemana {
    SEGUNDA(1, "Segunda-feira"),
    TERCA(2, "Terça-feira"),
    QUARTA(3, "Quarta-feira"),
    QUINTA(4, "Quinta-feira"),
    SEXTA(5, "Sexta-feira"),
    SABADO(6, "Sábado"),
    DOMINGO(7, "Domingo");

    private final int numero;
    private final String nome;

    DiaSemana(int numero, String nome) {
        this.numero = numero;
        this.nome = nome;
    }

    public static DiaSemana de(int numero) {
        for (DiaSemana d : values()) {
            if (d.numero == numero) {
                return d;
            }
        }
        throw new IllegalArgumentException("Dia da semana inválido: " + numero + " (use 1 a 7)");
    }

    public int getNumero() {
        return numero;
    }

    public String getNome() {
        return nome;
    }
}
