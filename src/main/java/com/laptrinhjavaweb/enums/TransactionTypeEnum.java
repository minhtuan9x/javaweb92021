package com.laptrinhjavaweb.enums;

public enum TransactionTypeEnum {
    CSKH("QUÁ TRÌNH CSKH"),
    DANDIXEM("DẪN ĐI XEM");

    private final String typeValue;
    public String getTypeValue() {
        return typeValue;
    }
    private TransactionTypeEnum(String typeValue) {
        this.typeValue = typeValue;
    }
}
