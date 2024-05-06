package com.picpaychallenge.common.domain.model.valueobjects.cpf;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Converter
public class CPFConverter implements AttributeConverter<Document, String> {

    @Override
    public String convertToDatabaseColumn(Document attribute) {
        return !ObjectUtils.isEmpty(attribute) && StringUtils.hasText(attribute.getValue()) ? attribute.getValue().replaceAll("\\D", "") : null;
    }

    @Override
    public CPF convertToEntityAttribute(String dbData) {
        return new CPF(dbData);
    }
}