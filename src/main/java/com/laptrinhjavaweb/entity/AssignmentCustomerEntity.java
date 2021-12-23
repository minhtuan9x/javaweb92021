package com.laptrinhjavaweb.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignmentcustomer")
public class AssignmentCustomerEntity extends BaseEntity{

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public CustomerEntity getCustomerEntity() {
        return customerEntity;
    }

    public void setCustomerEntity(CustomerEntity customerEntity) {
        this.customerEntity = customerEntity;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid",nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customerid",nullable = false)
    private CustomerEntity customerEntity;
}
