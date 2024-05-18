package com.picpaychallenge.transaction.payload;

import java.math.BigDecimal;

public record TransferDTO(Long payerId, Long payeeId, BigDecimal value ) {
}
