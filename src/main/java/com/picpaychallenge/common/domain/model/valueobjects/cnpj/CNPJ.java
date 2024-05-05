package com.picpaychallenge.common.domain.model.valueobjects.cnpj;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@NoArgsConstructor
@Getter
@Setter
public class CNPJ {
    @org.hibernate.validator.constraints.br.CNPJ
    private String value;

    public CNPJ(String value) {
        this.value = value.replaceAll("\\D", "");
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
