package com.picpaychallenge.common.domain.model.valueobjects.document;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Document {

    protected String value;

    public void setValue(String value) {
        this.value = value;
    }
}
