package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.custom.RentAreaRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RentAreaRepository extends RentAreaRepositoryCustom, JpaRepository<RentAreaEntity,Long> {
    List<RentAreaEntity> findByBuildingEntity(BuildingEntity buildingEntity);
    void deleteByBuildingEntity_Id(Long id);
}
