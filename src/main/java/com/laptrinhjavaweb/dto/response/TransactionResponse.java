package com.laptrinhjavaweb.dto.response;

public class TransactionResponse {
    private String createDateStr;
    private String note;

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TransactionResponse(){}

    public TransactionResponse(String createDateStr, String note) {
        this.createDateStr = createDateStr;
        this.note = note;
    }
}
