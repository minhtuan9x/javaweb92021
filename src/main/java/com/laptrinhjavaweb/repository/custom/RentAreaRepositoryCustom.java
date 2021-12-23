package com.laptrinhjavaweb.repository.custom;

import com.laptrinhjavaweb.dto.RentAreaDTO;
import com.laptrinhjavaweb.entity.RentAreaEntity;

import java.util.List;

public interface RentAreaRepositoryCustom {
    void saveAllByBuilding(List<RentAreaEntity> rentAreaEntitis, List<RentAreaEntity> rentAreaEntityListByBuilding);
}
