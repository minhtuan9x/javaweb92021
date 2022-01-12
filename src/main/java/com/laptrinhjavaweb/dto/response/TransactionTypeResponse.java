package com.laptrinhjavaweb.dto.response;

import java.util.ArrayList;
import java.util.List;

public class TransactionTypeResponse{
    private String code;
    private String typeValue;
    private List<TransactionResponse> transaction = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTypeValue() {
        return typeValue;
    }

    public void setTypeValue(String typeValue) {
        this.typeValue = typeValue;
    }

    public List<TransactionResponse> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<TransactionResponse> transaction) {
        this.transaction = transaction;
    }

}
