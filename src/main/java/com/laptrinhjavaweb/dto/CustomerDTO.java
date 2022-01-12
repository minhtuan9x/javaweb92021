package com.laptrinhjavaweb.dto;

import com.laptrinhjavaweb.dto.response.TransactionTypeResponse;

import java.util.List;

public class CustomerDTO extends AbstractDTO {
    private String fullName;
    private String phone;
    private String email;
    private List<TransactionTypeResponse> transactionResponseList;

    public List<TransactionTypeResponse> getTransactionResponseList() {
        return transactionResponseList;
    }

    public void setTransactionResponseList(List<TransactionTypeResponse> transactionResponseList) {
        this.transactionResponseList = transactionResponseList;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
