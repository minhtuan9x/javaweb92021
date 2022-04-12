package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.TransactionConverter;
import com.laptrinhjavaweb.dto.request.TransactionRequest;
import com.laptrinhjavaweb.entity.TransactionEntity;
import com.laptrinhjavaweb.repository.TransactionRepository;
import com.laptrinhjavaweb.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionConverter transactionConverter;

    @Override
    @Transactional
    public void save(TransactionRequest transactionRequest) {
        try {
            TransactionEntity transactionEntity = transactionConverter.toTransactionEntity(transactionRequest);
            if(transactionEntity!=null)
                transactionRepository.save(transactionEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
