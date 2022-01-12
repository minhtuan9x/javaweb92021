package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.dto.response.CustomerResponse;
import com.laptrinhjavaweb.dto.response.TransactionResponse;
import com.laptrinhjavaweb.dto.response.TransactionTypeResponse;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.entity.TransactionEntity;
import com.laptrinhjavaweb.enums.TransactionTypeEnum;
import com.laptrinhjavaweb.utils.DateUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CustomerConverter {
    @Autowired
    private ModelMapper modelMapper;
    public CustomerResponse toCustomerResponse(CustomerEntity customerEntity){
        CustomerResponse customerResponse = modelMapper.map(customerEntity,CustomerResponse.class);
        customerResponse.setCreateDateStr(DateUtils.toDateString(Optional.ofNullable(customerEntity.getCreatedDate()).orElse(new Date())));
        String staffNames = customerEntity.getUserEntities().stream().map(item->item.getFullName()).collect(Collectors.joining(","));
        customerResponse.setStaffNames(staffNames);
        return customerResponse;
    }
    public CustomerEntity toCustomerEntity(CustomerDTO customerDTO){
        return modelMapper.map(customerDTO,CustomerEntity.class);
    }
    public CustomerDTO toCustomerDTO(CustomerEntity customerEntity){
        CustomerDTO customerDTO = modelMapper.map(customerEntity,CustomerDTO.class);
        List<TransactionTypeResponse> transactionResponseList = new ArrayList<>();
        for(TransactionTypeEnum item:TransactionTypeEnum.values()){
            TransactionTypeResponse transactionResponse = new TransactionTypeResponse();
            transactionResponse.setCode(item.name());
            transactionResponse.setTypeValue(item.getTypeValue());
            List<TransactionResponse> transactionResponses = new ArrayList<>();
            for(TransactionEntity element: customerEntity.getTransactionEntities() ){
                if(item.name().equals(element.getCode())){
                    transactionResponses.add(new TransactionResponse(DateUtils.toDateString(Optional.ofNullable(element.getCreatedDate()).orElse(new Date())),element.getNote()));
                }
            }
            transactionResponse.setTransaction(transactionResponses);
            transactionResponseList.add(transactionResponse);
        }
        customerDTO.setTransactionResponseList(transactionResponseList);
        return customerDTO;
    }
}
