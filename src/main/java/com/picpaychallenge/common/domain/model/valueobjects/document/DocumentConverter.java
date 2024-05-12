package com.picpaychallenge.common.domain.model.valueobjects.document;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public interface DocumentConverter extends AttributeConverter<Document, String> {
}
