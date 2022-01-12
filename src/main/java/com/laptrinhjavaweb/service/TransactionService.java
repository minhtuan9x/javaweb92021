package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.request.TransactionRequest;

public interface TransactionService {
    void save(TransactionRequest transactionRequest);
}
