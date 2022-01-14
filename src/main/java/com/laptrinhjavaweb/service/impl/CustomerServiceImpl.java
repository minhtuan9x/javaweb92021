package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.constant.SystemConstant;
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
            return Optional.of(customerRepository.findAll(toCustomerSearchBuilder(customerSearchRequest)).stream().map(item->customerConverter.toCustomerResponse(item)).collect(Collectors.toList())).orElse(new ArrayList<>());
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public void assignmentCustomer(List<Long> staffIds, Long customerId) throws NotFoundException {
        CustomerEntity customerEntity = Optional.ofNullable(customerRepository.findOne(customerId)).orElseThrow(() ->new NotFoundException(SystemConstant.NF_CUSTOMER));
        customerEntity.setUserEntities(Optional.ofNullable(userRepository.findAll(staffIds)).orElseThrow(()->new NotFoundException(SystemConstant.NF_CUSTOMER)));
        customerRepository.save(customerEntity);
    }

    @Override
    @Transactional
    public List<Long> delete(List<Long> customerIds) throws NotFoundException {
        if(customerRepository.findAll(customerIds).size()!=customerIds.size())
            throw new NotFoundException(SystemConstant.NF_CUSTOMER);
        return customerRepository.deleteByIdIn(customerIds);
    }

    @Override
    public CustomerDTO save(CustomerDTO customerDTO) throws NotFoundException {
        CustomerEntity customerEntity = customerConverter.toCustomerEntity(customerDTO);
        if(customerDTO.getId()!=null){
            CustomerEntity customerEntityFound = Optional.ofNullable(customerRepository.findOne(customerDTO.getId())).orElseThrow(()->new NotFoundException(SystemConstant.NF_CUSTOMER));
            customerEntity.setUserEntities(customerEntityFound.getUserEntities());
            customerEntity.setTransactionEntities(customerEntityFound.getTransactionEntities());
        }
        return customerConverter.toCustomerDTO(customerRepository.save(customerEntity));
    }

    @Override
    public CustomerDTO findById(Long id) {
        return id!=null?customerConverter.toCustomerDTO(Optional.ofNullable(customerRepository.findOne(id)).orElse(new CustomerEntity())):new CustomerDTO();
    }
    private CustomerSearchBuilder toCustomerSearchBuilder(CustomerSearchRequest customerSearchRequest){
        return new CustomerSearchBuilder.Builder()
                .setFullName(customerSearchRequest.getFullName())
                .setPhone(customerSearchRequest.getPhone())
                .setEmail(customerSearchRequest.getEmail())
                .setStaffId(customerSearchRequest.getStaffId())
                .build();
    }
}
