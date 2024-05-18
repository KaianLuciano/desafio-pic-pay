package com.picpaychallenge.common.domain.model.valueobjects.cnpj;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import com.picpaychallenge.common.domain.model.valueobjects.document.DocumentConverter;
import jakarta.persistence.Converter;

@Converter
public class CNPJConverter implements DocumentConverter {

    @Override
    public String convertToDatabaseColumn(Document attribute) {
        return attribute.getValue().replaceAll("[^0-9]", "");
    }

    @Override
    public CNPJ convertToEntityAttribute(String dbData) {
        return new CNPJ(dbData);
    }
}
