package com.laptrinhjavaweb.builder;

import com.laptrinhjavaweb.anotation.LikeField;
import com.laptrinhjavaweb.anotation.OperatorField;
import com.laptrinhjavaweb.anotation.SearchObject;

@SearchObject(tableName = "customer",aliasValue = "c")
public class CustomerSearchBuilder {
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

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public Long getStaffId() {
        return staffId;
    }

    private CustomerSearchBuilder(Builder builder){
        this.fullName = builder.fullName;
        this.email = builder.email;
        this.phone = builder.phone;
        this.staffId = builder.staffId;
    }
    public static class Builder{
        private String fullName;
        private String phone;
        private String email;
        private Long staffId;

        public Builder setFullName(String fullName){
            this.fullName = fullName;
            return this;
        }
        public Builder setPhone(String phone){
            this.phone = phone;
            return this;
        }
        public Builder setEmail(String email){
            this.email = email;
            return this;
        }
        public Builder setStaffId(Long staffId){
            this.staffId = staffId;
            return this;
        }
        public CustomerSearchBuilder build(){
            return new CustomerSearchBuilder(this);
        }
    }
}
