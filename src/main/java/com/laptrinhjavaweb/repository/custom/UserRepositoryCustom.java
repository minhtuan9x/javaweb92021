package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.entity.UserEntity;

import java.util.List;

public interface UserRepositoryCustom {
    List<UserEntity> getAllStaff();
    List<UserEntity> getAllStaffByBuildingID(Long buildingid);
    List<UserEntity> getAllStaffByCustomerID(Long customerId);
}
