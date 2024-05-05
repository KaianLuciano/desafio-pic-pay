package com.picpaychallenge.common.domain.model.valueobjects.cpf;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@NoArgsConstructor
@Getter
@Setter
public class CPF {
    @org.hibernate.validator.constraints.br.CPF
    private String value;

    public CPF(String value) {
        this.value = StringUtils.hasText(value) ? value.replaceAll("\\D", "") : null;
    }


    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this, o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
