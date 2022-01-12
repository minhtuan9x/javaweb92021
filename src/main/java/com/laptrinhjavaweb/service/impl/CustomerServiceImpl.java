package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.CustomerConverter;
import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.dto.request.CustomerSearchRequest;
import com.laptrinhjavaweb.dto.response.CustomerResponse;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.repository.CustomerRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.CustomerService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private CustomerConverter customerConverter;
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CustomerResponse> findAll(CustomerSearchRequest customerSearchRequest) {
        try {
            return Optional.of(customerRepository.findAll(customerSearchRequest).stream().map(item->customerConverter.toCustomerResponse(item)).collect(Collectors.toList())).orElse(new ArrayList<>());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void assignmentCustomer(List<Long> staffIds, Long customerId) throws NotFoundException {
        CustomerEntity customerEntity = Optional.ofNullable(customerRepository.findOne(customerId)).orElseThrow(() ->new NotFoundException("Not found Customer"));
        customerEntity.setUserEntities(Optional.ofNullable(userRepository.findAll(staffIds)).orElseThrow(()->new NotFoundException("Not Found Customer")));
        customerRepository.save(customerEntity);
    }

    @Override
    @Transactional
    public List<Long> delete(List<Long> customerIds) {
        return customerRepository.deleteByIdIn(customerIds);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = customerConverter.toCustomerEntity(customerDTO);
        if(customerDTO.getId()!=null){
            CustomerEntity customerEntityFound = customerRepository.findOne(customerDTO.getId());
            customerEntity.setCreatedDate(customerEntityFound.getCreatedDate());
            customerEntity.setUserEntities(customerEntityFound.getUserEntities());
            customerEntity.setTransactionEntities(customerEntityFound.getTransactionEntities());
        }
        return customerConverter.toCustomerDTO(customerRepository.save(customerEntity));
    }

    @Override
    public CustomerDTO findById(Long id) {
        return id!=null?customerConverter.toCustomerDTO(Optional.ofNullable(customerRepository.findOne(id)).orElse(new CustomerEntity())):new CustomerDTO();
    }
}