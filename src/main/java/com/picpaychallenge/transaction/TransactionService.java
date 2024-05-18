package com.picpaychallenge.transaction;

import com.picpaychallenge.transaction.payload.TransferDTO;

import java.util.List;

public interface TransactionService {
    void transfer(TransferDTO transferDTO);
    List<Transaction> getAllTransactions();
}
