package com.laptrinhjavaweb.entity;

import javax.persistence.*;

@Entity
@Table(name = "assignmentbuilding")
public class AssignmentBuildingEntity extends BaseEntity {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buildingid",nullable = false)
    private BuildingEntity buildingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "staffid",nullable = false)
    private UserEntity userEntity;

    public BuildingEntity getBuildingEntity() {
        return buildingEntity;
    }

    public void setBuildingEntity(BuildingEntity buildingEntity) {
        this.buildingEntity = buildingEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }
    public AssignmentBuildingEntity(){

    }
    public AssignmentBuildingEntity(BuildingEntity buildingEntity, UserEntity userEntity) {
        this.buildingEntity = buildingEntity;
        this.userEntity = userEntity;
    }
}
