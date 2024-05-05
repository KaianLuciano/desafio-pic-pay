package com.picpaychallenge.common.domain.model.valueobjects.cpf;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Converter
public class CPFConverter implements AttributeConverter<CPF, String> {

    @Override
    public String convertToDatabaseColumn(CPF cpfAttribute) {
        return !ObjectUtils.isEmpty(cpfAttribute) && StringUtils.hasText(cpfAttribute.getValue()) ? cpfAttribute.getValue().replaceAll("\\D", "") : null;
    }

    @Override
    public CPF convertToEntityAttribute(String dbData) {
        return new CPF(dbData);
    }
}