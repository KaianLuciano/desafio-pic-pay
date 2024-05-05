package com.picpaychallenge.common.domain.model.valueobjects.document;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@MappedSuperclass
@Getter
@Setter
public abstract class Document {

    protected String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
