package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.custom.RentAreaRepositoryCustom;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RentAreaRepositoryImpl implements RentAreaRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Transactional
    @Override
    public void saveAllByBuilding(List<RentAreaEntity> rentAreaEntitis, List<RentAreaEntity> rentAreaEntityListByBuilding) {
        for(RentAreaEntity item : rentAreaEntityListByBuilding){
            int flag = 0;
            for(RentAreaEntity item2 : rentAreaEntitis){
                if(item.getValue()==item2.getValue()){
                    flag++;
                }
            }
            if(flag==0)
                entityManager.remove(item);
        }
        for(RentAreaEntity item : rentAreaEntitis){
            int flag = 0;
            for(RentAreaEntity item2 :rentAreaEntityListByBuilding){
                if(item.getValue()==item2.getValue()){
                    flag++;
                }
            }
            if(flag==0)
                entityManager.persist(item);
        }
    }
}
