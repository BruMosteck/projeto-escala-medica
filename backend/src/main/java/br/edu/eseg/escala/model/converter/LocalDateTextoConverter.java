package br.edu.eseg.escala.model.converter;

import java.time.LocalDate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * O SQLite não tem tipo DATE nativo: a data fica gravada como TEXT no formato ISO (AAAA-MM-DD).
 * Este converter traduz entre o texto do banco e o LocalDate usado no Java.
 */
@Converter
public class LocalDateTextoConverter implements AttributeConverter<LocalDate, String> {

    @Override
    public String convertToDatabaseColumn(LocalDate data) {
        return data == null ? null : data.toString();
    }

    @Override
    public LocalDate convertToEntityAttribute(String texto) {
        return texto == null ? null : LocalDate.parse(texto);
    }
}
