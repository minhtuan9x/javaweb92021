package com.dominhtuan.jdbc.impl;

import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.entity.BuildingEntity;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.util.CheckInputUtil;
import com.dominhtuan.util.ConnectDBUtil;
import jdk.nashorn.internal.scripts.JO;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BuildingJDBCImpl implements BuildingJDBC {

    private Connection conn = null;
    private Statement stmt;
    private ResultSet rs;

    @Override
    public List<BuildingEntity> findBuilding(BuildingSearchRequest buildingSearchRequest) throws SQLException {
        List<BuildingEntity> buildingEntities = new ArrayList<>();
        try {
            conn = ConnectDBUtil.connectDB();
            conn.setAutoCommit(false);
            if (conn != null) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(buildQuery(buildingSearchRequest));
                while (rs.next()) {
                    BuildingEntity buildingEntity = new BuildingEntity();
                    buildingEntity.setId(rs.getLong("id"));
                    buildingEntity.setName(rs.getString("name"));
                    buildingEntity.setStreet(rs.getString("street"));
                    buildingEntity.setWard(rs.getString("ward"));
                    buildingEntity.setDistrictID(rs.getLong("districtid"));
                    buildingEntity.setManagerName(rs.getString("managername"));
                    buildingEntity.setManagerPhone(rs.getString("managerphone"));
                    buildingEntity.setFloorArea(rs.getInt("floorarea"));
                    buildingEntity.setRentPriceDescription(rs.getString("rentpricedescription"));
                    buildingEntity.setRentPrice(rs.getInt("rentprice"));
                    buildingEntity.setServiceFee(rs.getString("servicefee"));
                    buildingEntities.add(buildingEntity);
                }
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            System.out.println("Loi jdbc");
            e.printStackTrace();
        } finally {
            if (conn != null)
                conn.close();
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        }

        return buildingEntities;
    }

    public void buildQueryWithJoin(BuildingSearchRequest buildingSearchRequest, StringBuilder join, StringBuilder where) {


        if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaFrom()) || CheckInputUtil.isValid(buildingSearchRequest.getRentAreaTo())) {
            join.append("\ninner join rentarea as ra on bd.id = ra.buildingid ");
            if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaTo())) {
                where.append("\nand ra.value <= " + buildingSearchRequest.getRentAreaTo() + " ");
            }
            if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaFrom())) {
                where.append("\nand ra.value >= " + buildingSearchRequest.getRentAreaFrom() + " ");
            }
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getDistrictCode())) {
            where.append("\nand dt.code ='" + buildingSearchRequest.getDistrictCode() + "' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getStaffID())) {
            join.append("\ninner join assignmentbuilding as ab on bd.id = ab.buildingid inner join user as u on ab.staffid = u.id ");
            where.append("\nand g.id = " + buildingSearchRequest.getStaffID());
        }

        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            join.append("\ninner join buildingrenttype as br on bd.id = br.buildingid \ninner join renttype as rt on rt.id = br.renttypeid ");

//            String queryTypes = buildingSearchRequest.getRentTypes()
//                    .stream()
//                    .map(i -> "'" + i + "'")
//                    .collect(Collectors.joining(","));
//            where.append("\nand e.code in (").append(queryTypes).append(")");

            List<String> buildingTypes = new ArrayList<>();
            for (String type : buildingSearchRequest.getRentTypes()) {
                buildingTypes.add("'" + type + "'");
            }
            where.append("\nAND rt.code IN (")
                    .append(String.join(",", buildingTypes))
                    .append(")");
        }

    }

    public void buildQueryWithoutJoin(BuildingSearchRequest buildingSearchRequest, StringBuilder where) {
        if (CheckInputUtil.isValid(buildingSearchRequest.getBuildingName())) {
            where.append("\nand bd.name like '%" + buildingSearchRequest.getBuildingName() + "%'");
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getFloorArea())) {
            where.append("\nand bd.floorarea = " + buildingSearchRequest.getFloorArea());
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getWard())) {
            where.append("\nand bd.ward like '%" + buildingSearchRequest.getWard() + "%' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getStreet())) {
            where.append("\nand bd.street like '%" + buildingSearchRequest.getStreet() + "%' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getNumberOfBasement())) {
            where.append("\nand bd.numberofbasement = " + buildingSearchRequest.getNumberOfBasement() + " ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getDirection())) {
            where.append("\nand bd.direction ='" + buildingSearchRequest.getDirection() + "' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getLevel())) {
            where.append("\nand bd.level ='" + buildingSearchRequest.getLevel() + "' ");
        }


        if (CheckInputUtil.isValid(buildingSearchRequest.getRentPriceTo())) {
            where.append("\nand bd.rentprice <= " + buildingSearchRequest.getRentPriceTo() + " ");
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getRentPriceFrom())) {
            where.append("\nand bd.rentprice >= " + buildingSearchRequest.getRentPriceFrom() + " ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getManagerName())) {
            where.append("\nand bd.managername like '%" + buildingSearchRequest.getManagerName() + "%'");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getManagerPhone())) {
            where.append("\nand bd.managerphone like '%" + buildingSearchRequest.getManagerPhone() + "%'");
        }
    }

    public String buildQuery(BuildingSearchRequest buildingSearchRequest) {


        StringBuilder queryFinal = new StringBuilder("select bd.id,bd.name,bd.street,bd.ward,bd.districtid,bd.managername," +
                "bd.managerphone,bd.floorarea,bd.rentpricedescription,bd.rentprice,bd.servicefee from building as bd ");
        StringBuilder join = new StringBuilder("\ninner join district as dt on bd.districtid = dt.id ");
        StringBuilder where = new StringBuilder("where 1=1 ");
        buildQueryWithJoin(buildingSearchRequest, join, where);
        buildQueryWithoutJoin(buildingSearchRequest, where);
        where.append("\ngroup by bd.id");
        queryFinal.append(join).append(where);
        System.out.println("===================================");
        System.out.println(queryFinal.toString());
        return queryFinal.toString();
    }
}
