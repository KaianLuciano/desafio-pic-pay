package com.picpaychallenge.common.domain.model.valueobjects.cnpj;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CNPJConverter implements AttributeConverter<CNPJ, String> {

    @Override
    public String convertToDatabaseColumn(CNPJ attribute) {
        return attribute.getValue().replaceAll("[^0-9]", "");
    }

    @Override
    public CNPJ convertToEntityAttribute(String dbData) {
        return new CNPJ(dbData);
    }
}
