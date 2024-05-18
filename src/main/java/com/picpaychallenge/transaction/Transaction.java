package com.picpaychallenge.transaction;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;

@Entity
@Table(name = "tb_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Audited
public class Transaction {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long transactionId;
    Long payerId;
    Long payeeId;
    BigDecimal value;

    public Transaction(Long payerId, Long payeeId, BigDecimal value) {
        this.payerId = payerId;
        this.payeeId = payeeId;
        this.value = value;
    }

}
