package com.picpaychallenge.common.domain.model.valueobjects.cnpj;

import com.picpaychallenge.common.domain.model.valueobjects.document.Document;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@NoArgsConstructor
@Getter
@Setter
public class CNPJ extends Document {

    public CNPJ(String value) {
        this.value = value.replaceAll("\\D", "");
    }

    @Override
    @org.hibernate.validator.constraints.br.CNPJ
    public String getValue() {
        return super.getValue();
    }

    @Override
    @org.hibernate.validator.constraints.br.CNPJ
    public void setValue(String value) {
        super.setValue(value);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        return "CNPJ{" +
                "value='" + value + '\'' +
                '}';
    }
}
