package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.request.TransactionRequest;
import com.laptrinhjavaweb.entity.TransactionEntity;
import com.laptrinhjavaweb.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CustomerRepository customerRepository;

    public TransactionEntity toTransactionEntity(TransactionRequest transactionRequest) {
        TransactionEntity transactionEntity = new TransactionEntity();
        transactionEntity.setCode(transactionRequest.getCode());
        transactionEntity.setNote(transactionRequest.getNote());
        if (transactionEntity.getNote().isEmpty() || transactionEntity.getNote() == null)
            return null;
        transactionEntity.setCustomerEntity(customerRepository.findOne(transactionRequest.getCustomerId()));
        return transactionEntity;
    }
}
