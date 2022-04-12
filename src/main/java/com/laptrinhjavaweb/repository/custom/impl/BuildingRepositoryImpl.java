package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import com.laptrinhjavaweb.utils.SqlUtils;
import com.laptrinhjavaweb.utils.ValidateUtil;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        try {
            String sql = SqlUtils.toQuery(buildingSearchBuilder,buildDifferent(buildingSearchBuilder));
            System.out.println("tuan"+sql);
            Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String buildDifferent(BuildingSearchBuilder buildingSearchBuilder) {
        StringBuilder different = new StringBuilder();
        ///join
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaTo())
                || ValidateUtil.isValid(buildingSearchBuilder.getRentAreaFrom()))
            different.append("join:inner join rentarea as ra on bd.id = ra.buildingid,");
        if (ValidateUtil.isValid(buildingSearchBuilder.getStaffID()))
            different.append("join:inner join assignmentbuilding as ab on bd.id = ab.buildingid inner join user as u on ab.staffid = u.id,");
        //query
        if (ValidateUtil.isValid(buildingSearchBuilder.getStaffID())) {
            different.append("query:u.id = " + buildingSearchBuilder.getStaffID()+",");
        }
        if (buildingSearchBuilder.getRentTypes() != null && buildingSearchBuilder.getRentTypes().size() > 0) {
            different.append("query:(");
            String renttypes = buildingSearchBuilder.getRentTypes().stream()
                    .map(item -> ("bd.type like '%" + item + "%'")).collect(Collectors.joining(" or "));
            different.append(renttypes);
            different.append(" ),");
        }
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaFrom())) {
            different.append("query:EXISTS (select * from rentarea as ra where bd.id=ra.buildingid and ra.value >= "
                    + buildingSearchBuilder.getRentAreaFrom() + "),");
        }
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaTo())) {
            different.append("query:EXISTS (select * from rentarea as ra where bd.id=ra.buildingid and ra.value <= "
                    + buildingSearchBuilder.getRentAreaTo() + "),");
        }
        return different.toString();
    }
}
