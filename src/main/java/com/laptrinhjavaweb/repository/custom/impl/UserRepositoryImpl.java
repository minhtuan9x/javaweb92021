package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.custom.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<UserEntity> getAllStaff() {
        StringBuilder sql = new StringBuilder("Select * from user as u inner join user_role as ur on u.id = ur.userid ");
        sql.append("where ur.roleid = 2 and u.status = 1");
        Query query = entityManager.createNativeQuery(sql.toString(),UserEntity.class);
        return query.getResultList();
    }

    @Override
    public List<UserEntity> getAllStaffByBuildingID(Long buildingid) {
        StringBuilder sql =new StringBuilder("Select * from user as u inner join assignmentbuilding as ab on u.id = staffid ");
        sql.append("where ab.buildingid = ").append(buildingid).append(" and u.status = 1");
        Query query = entityManager.createNativeQuery(sql.toString(),UserEntity.class);
        return query.getResultList();
    }

    @Override
    public List<UserEntity> getAllStaffByCustomerID(Long customerId) {
        StringBuilder sql =new StringBuilder("Select * from user as u inner join assignmentcustomer as ac on u.id = staffid ");
        sql.append("where ac.customerid = ").append(customerId).append(" and u.status = 1");
        Query query = entityManager.createNativeQuery(sql.toString(),UserEntity.class);
        return query.getResultList();
    }
}
