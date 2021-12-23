package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity,Long> {
    AssignmentBuildingEntity findByBuildingEntityAndUserEntity(BuildingEntity buildingEntity, UserEntity userEntity);
}
