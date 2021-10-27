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
            conn.close();
            rs.close();
            stmt.close();
        }

        return buildingEntities;
    }

    public void buildQueryWithJoin(BuildingSearchRequest buildingSearchRequest, StringBuilder join, StringBuilder where) {


        if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaFrom()) || CheckInputUtil.isValid(buildingSearchRequest.getRentAreaTo())) {
            join.append("\ninner join rentarea as c on a.id = c.buildingid ");
            if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaTo())) {
                where.append("\nand c.value <= " + buildingSearchRequest.getRentAreaTo() + " ");
            }
            if (CheckInputUtil.isValid(buildingSearchRequest.getRentAreaFrom())) {
                where.append("\nand c.value >= " + buildingSearchRequest.getRentAreaFrom() + " ");
            }
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getDistrictCode())) {
            join.append("\ninner join district as b on a.districtid = b.id ");
            where.append("\nand b.code ='" + buildingSearchRequest.getDistrictCode() + "' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getStaffID())) {
            join.append("\ninner join assignmentbuilding as f on a.id = f.buildingid inner join user as g on f.staffid = g.id ");
            where.append("\nand g.id = " + buildingSearchRequest.getStaffID());
        }

        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            join.append("\ninner join buildingrenttype as d on a.id = d.buildingid \ninner join renttype as e on e.id = d.renttypeid ");

            String queryTypes = buildingSearchRequest.getRentTypes()
                    .stream()
                    .map(i -> "'" + i + "'")
                    .collect(Collectors.joining(","));
            where.append("\nand e.code in (").append(queryTypes).append(")");
        }

    }

    public void buildQueryWithoutJoin(BuildingSearchRequest buildingSearchRequest, StringBuilder where) {
        if (CheckInputUtil.isValid(buildingSearchRequest.getBuildingName())) {
            where.append("\nand a.name like '%" + buildingSearchRequest.getBuildingName() + "%'");
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getFloorArea())) {
            where.append("\nand a.floorarea = " + buildingSearchRequest.getFloorArea());
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getWard())) {
            where.append("\nand a.ward like '%" + buildingSearchRequest.getWard() + "%' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getStreet())) {
            where.append("\nand a.street like '%" + buildingSearchRequest.getStreet() + "%' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getNumberOfBasement())) {
            where.append("\nand a.numberofbasement = " + buildingSearchRequest.getNumberOfBasement() + " ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getDirection())) {
            where.append("\nand a.direction ='" + buildingSearchRequest.getDirection() + "' ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getLevel())) {
            where.append("\nand a.level ='" + buildingSearchRequest.getLevel() + "' ");
        }


        if (CheckInputUtil.isValid(buildingSearchRequest.getRentPriceTo())) {
            where.append("\nand a.rentprice <= " + buildingSearchRequest.getRentPriceTo() + " ");
        }
        if (CheckInputUtil.isValid(buildingSearchRequest.getRentPriceFrom())) {
            where.append("\nand a.rentprice >= " + buildingSearchRequest.getRentPriceFrom() + " ");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getManagerName())) {
            where.append("\nand a.managername like '%" + buildingSearchRequest.getManagerName() + "%'");
        }

        if (CheckInputUtil.isValid(buildingSearchRequest.getManagerPhone())) {
            where.append("\nand a.managerphone like '%" + buildingSearchRequest.getManagerPhone() + "%'");
        }
    }

    public String buildQuery(BuildingSearchRequest buildingSearchRequest) {
        StringBuilder queryFinal = new StringBuilder("select * from building as a ");
        StringBuilder join = new StringBuilder("");
        StringBuilder where = new StringBuilder("where 1=1 ");
        buildQueryWithJoin(buildingSearchRequest, join, where);
        buildQueryWithoutJoin(buildingSearchRequest, where);
        where.append("\ngroup by a.id");
        queryFinal.append(join).append(where);
        System.out.println("===================================");
        System.out.println(queryFinal.toString());
        return queryFinal.toString();
    }
}
