package com.laptrinhjavaweb.repository.custom.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.AssignmentBuildingEntity;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.repository.AssignmentBuildingRepository;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.repository.custom.BuildingRepositoryCustom;
import com.laptrinhjavaweb.utils.ValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AssignmentBuildingRepository assignmentBuildingRepository;
    @Autowired
    private RentAreaRepository rentAreaRepository;

    @Override
    public List<BuildingEntity> findAll(BuildingSearchBuilder buildingSearchBuilder) {
        try {
            String sql = buildQuery(buildingSearchBuilder);
            Query query = entityManager.createNativeQuery(sql, BuildingEntity.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public String buildQuery(BuildingSearchBuilder buildingSearchBuilder) {

        StringBuilder queryFinal = new StringBuilder("select * from building as bd ");
//		bd.id,bd.name,bd.street,bd.ward,bd.district,bd.managername,"
//				+ "bd.managerphone,bd.floorarea,bd.rentpricedescription,bd.rentprice,bd.servicefee
        StringBuilder join = new StringBuilder();
        buildJoinQuery(join, buildingSearchBuilder);
        StringBuilder where = new StringBuilder("\nwhere 1=1 ");
        List<String> likeFields = Arrays.asList("name", "ward", "street", "managername", "managerphone");
        List<String> operatorFields = Arrays.asList("floorarea", "district", "numberOfBasement", "direction", "level");
        buildQueryPart1(buildingSearchBuilder, where, likeFields, operatorFields);
        buildQueryPart2(buildingSearchBuilder, where);
        where.append("\ngroup by bd.id");
        queryFinal.append(join).append(where);
        System.out.println("===================================");
        System.out.println(queryFinal.toString());
        return queryFinal.toString();
    }

    public void buildJoinQuery(StringBuilder join, BuildingSearchBuilder buildingSearchBuilder) {
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaTo())
                || ValidateUtil.isValid(buildingSearchBuilder.getRentAreaFrom()))
            join.append("\ninner join rentarea as ra on bd.id = ra.buildingid ");
        if (ValidateUtil.isValid(buildingSearchBuilder.getStaffID()))
            join.append(
                    "\ninner join assignmentbuilding as ab on bd.id = ab.buildingid inner join user as u on ab.staffid = u.id ");
    }

    public void buildQueryPart1(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where,
                                List<String> likeFields, List<String> operatorFields) {
        try {
            Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(buildingSearchBuilder);
                if (ValidateUtil.isValid(fieldValue)) {
                    String fieldName = field.getName().toLowerCase();
                    for (String operatorField : operatorFields) {
                        if (operatorField.equals(fieldName)) {
                            if (field.getType().toString().equals("class java.lang.String")) {
                                where.append(String.format("\nand bd.%s = '%s'", fieldName, fieldValue.toString()));
                                break;
                            }
                            if (field.getType().toString().equals("class java.lang.Integer")) {
                                where.append(String.format("\nand bd.%s = %s", fieldName, fieldValue.toString()));
                                break;
                            }
                        }
                    }
                    for (String likeField : likeFields) {
                        if (likeField.equals(fieldName)) {
                            where.append(String.format("\nand bd.%s like '%s'", fieldName,
                                    "%" + fieldValue.toString() + "%"));
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildQueryPart2(BuildingSearchBuilder buildingSearchBuilder, StringBuilder where) {
        if (buildingSearchBuilder.getRentTypes() != null && buildingSearchBuilder.getRentTypes().size() > 0) {
            where.append("\nand (");
            String renttypes = buildingSearchBuilder.getRentTypes().stream()
                    .map(item -> ("bd.type like '%" + item + "%'")).collect(Collectors.joining(" or "));
            where.append(renttypes);
            where.append(" )");
        }

        if (ValidateUtil.isValid(buildingSearchBuilder.getStaffID())) {
            where.append("\nand u.id = " + buildingSearchBuilder.getStaffID());
        }

        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaFrom())) {
            where.append("\nand EXISTS (select * from rentarea as ra where bd.id=ra.buildingid and ra.value >= "
                    + buildingSearchBuilder.getRentAreaFrom() + ")");
        }
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentAreaTo())) {
            where.append("\nand EXISTS (select * from rentarea as ra where bd.id=ra.buildingid and ra.value <= "
                    + buildingSearchBuilder.getRentAreaTo() + ")");
        }

        if (ValidateUtil.isValid(buildingSearchBuilder.getRentPriceFrom())) {
            where.append("\nand bd.rentprice >= " + buildingSearchBuilder.getRentPriceFrom());
        }
        if (ValidateUtil.isValid(buildingSearchBuilder.getRentPriceTo())) {
            where.append("\nand bd.rentprice <= " + buildingSearchBuilder.getRentPriceTo());
        }
    }
}
