package com.laptrinhjavaweb.dto.response;

public abstract class BaseResponse {
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
