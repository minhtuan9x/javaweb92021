package com.laptrinhjavaweb.repository;

import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentBuildingRepository extends JpaRepository<AssignmentBuildingEntity,Long> {
    AssignmentBuildingEntity findByBuildingEntityAndUserEntity(BuildingEntity buildingEntity, UserEntity userEntity);
    void deleteByBuildingEntity_Id(Long buildingId);
    void deleteByBuildingEntity_IdIn(List<Long> buildingId);
    List<AssignmentBuildingEntity> findByBuildingEntity_Id(Long buildingId);
    void deleteByIdIn(List<Long> id);
}
