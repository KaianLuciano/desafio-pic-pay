package com.picpaychallenge.common.domain.model.valueobjects.cnpj;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CNPJConverter implements AttributeConverter<Document, String> {

    @Override
    public String convertToDatabaseColumn(Document attribute) {
        return attribute.getValue().replaceAll("[^0-9]", "");
    }

    @Override
    public CNPJ convertToEntityAttribute(String dbData) {
        return new CNPJ(dbData);
    }
}
