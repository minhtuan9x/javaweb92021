package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.builder.CustomerSearchBuilder;
import com.laptrinhjavaweb.dto.request.CustomerSearchRequest;
import com.laptrinhjavaweb.entity.CustomerEntity;
import com.laptrinhjavaweb.repository.custom.CustomerRepositoryCustom;
import com.laptrinhjavaweb.utils.SqlUtils;
import com.laptrinhjavaweb.utils.ValidateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public List<CustomerEntity> findAll(CustomerSearchBuilder customerSearchBuilder) {
        String sql = SqlUtils.toQuery(customerSearchBuilder,buildDiff(customerSearchBuilder));
        Query query = entityManager.createNativeQuery(sql,CustomerEntity.class);
        System.out.println("cusquery: "+sql);
        return query.getResultList();
    }
    public String buildDiff(CustomerSearchBuilder customerSearchBuilder){
        StringBuilder diff = new StringBuilder();
        if(ValidateUtil.isValid(customerSearchBuilder.getStaffId())){
         diff.append("join:inner join assignmentcustomer as ac on c.id = ac.customerid,")
                 .append("query:ac.staffid = ").append(customerSearchBuilder.getStaffId()).append(",");
        }
        return diff.toString();
    }
}
