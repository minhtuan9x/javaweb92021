package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.repository.custom.RentAreaRepositoryCustom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

public class RentAreaRepositoryImpl implements RentAreaRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    RentAreaRepository rentAreaRepository;

    @Transactional
    @Override
    public void saveAllByBuilding(List<RentAreaEntity> rentAreaEntitis, BuildingEntity buildingEntity) {
        List<RentAreaEntity> rentAreaEntityListByBuilding = new ArrayList<>();
        if (buildingEntity.getRentAreaEntities().size()>0){
            rentAreaEntityListByBuilding = rentAreaRepository.findByBuildingEntity(buildingEntity);
        }

        if(rentAreaEntitis.size()>0){
            rentAreaEntityListByBuilding.forEach(item->{
                entityManager.remove(item);
            });
            rentAreaEntitis.forEach(item->{
                entityManager.persist(item);
            });
        }
    }
}
