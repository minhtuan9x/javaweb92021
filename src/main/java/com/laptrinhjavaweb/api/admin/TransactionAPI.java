package com.laptrinhjavaweb.api.admin;

import com.laptrinhjavaweb.dto.request.TransactionRequest;
import com.laptrinhjavaweb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transaction")
public class TransactionAPI {
    @Autowired
    private TransactionService transactionService;
    @PostMapping
    public ResponseEntity<Void> save(@RequestBody TransactionRequest transactionRequest){
        transactionService.save(transactionRequest);
        return ResponseEntity.noContent().build();
    }
}
