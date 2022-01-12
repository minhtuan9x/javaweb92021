package com.laptrinhjavaweb.dto.request;

import com.laptrinhjavaweb.anotation.LikeField;
import com.laptrinhjavaweb.anotation.OperatorField;
import com.laptrinhjavaweb.anotation.SearchObject;

@SearchObject(tableName = "customer",aliasValue = "c")
public class CustomerSearchRequest {
    @LikeField
    private String fullName;
    @LikeField
    private String phone;
    @OperatorField
    private String email;
    private Long staffId;

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

    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }
}
