package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.dto.request.CustomerSearchRequest;
import com.laptrinhjavaweb.dto.response.CustomerResponse;
import javassist.NotFoundException;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> findAll(CustomerSearchRequest customerSearchRequest);
    void assignmentCustomer(List<Long> staffIds,Long customerId) throws NotFoundException;
    List<Long> delete(List<Long> customerIds) throws NotFoundException;
    CustomerDTO save(CustomerDTO customerDTO) throws NotFoundException;
    CustomerDTO findById(Long id);
}
