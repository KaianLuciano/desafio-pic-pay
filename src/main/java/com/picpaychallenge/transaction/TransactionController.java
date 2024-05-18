package com.picpaychallenge.transaction;

import com.picpaychallenge.transaction.payload.TransferDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("Transaction Controller")
@RequestMapping("${picpay.challenge.base.path}/${picpay.challenge.base.version}/transactions")
@RequiredArgsConstructor
@Tag(name = "Transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransferDTO transferDTO) {
        transactionService.transfer(transferDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Transaction>> getAllTransactions() {
        return ResponseEntity.ok(transactionService.getAllTransactions());
    }
}
