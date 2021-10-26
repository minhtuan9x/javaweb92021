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

@Repository
public class BuildingJDBCImpl implements BuildingJDBC {

    private ConnectDBUtil connectDBUtil = new ConnectDBUtil();
    private Connection conn = null;
    private Statement stmt;
    private ResultSet rs;
    private CheckInputUtil checkInputUtil = new CheckInputUtil();

    @Override
    public List<BuildingEntity> findBuilding(BuildingSearchRequest buildingSearchRequest) throws SQLException {
        List<BuildingEntity> buildingEntities = new ArrayList<>();
        try {
            conn = connectDBUtil.connectDB();
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

    public Map<String,String> buildQueryWithJoin(BuildingSearchRequest buildingSearchRequest) {
        Map<String,String> result = new HashMap<>();
        StringBuilder join = new StringBuilder();
        StringBuilder query = new StringBuilder();
        if (!checkInputUtil.isNull(buildingSearchRequest.getDistrictCode())) {
            join.append("\ninner join district as b on a.districtid = b.id ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaFrom()) || !checkInputUtil.isNull(buildingSearchRequest.getRentAreaTo())) {
            join.append("\ninner join rentarea as c on a.id = c.buildingid ");
        }
        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            join.append("\ninner join buildingrenttype as d on a.id = d.buildingid \ninner join renttype as e on e.id = d.renttypeid ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStaffID())) {
            join.append("\ninner join assignmentbuilding as f on a.id = f.buildingid inner join user as g on f.staffid = g.id ");
        }

        result.put("join",join.toString());

        if (!checkInputUtil.isNull(buildingSearchRequest.getDistrictCode())) {
            query.append("\nand b.code ='" + buildingSearchRequest.getDistrictCode() + "' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaTo())) {
            query.append("\nand c.value <= " + buildingSearchRequest.getRentAreaTo() + " ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaFrom())) {
            query.append("\nand c.value >= " + buildingSearchRequest.getRentAreaFrom() + " ");
        }

        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            query.append("\nand ( ");
            for (String item : buildingSearchRequest.getRentTypes()) {
                if (buildingSearchRequest.getRentTypes().indexOf(item) == buildingSearchRequest.getRentTypes().size() - 1) {
                    query.append("e.code = '" + item + "' ");
                } else {
                    query.append("e.code = '" + item + "' or ");
                }
            }
            query.append(" )");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStaffID())) {
            query.append("\nand g.id = " + buildingSearchRequest.getStaffID());
        }
        result.put("query",query.toString());
        return result;
    }

    public String buildQueryWithoutJoin(BuildingSearchRequest buildingSearchRequest){
        StringBuilder query = new StringBuilder();
        if (!checkInputUtil.isNull(buildingSearchRequest.getBuildingName())) {
            query.append("\nand a.name like '%" + buildingSearchRequest.getBuildingName() + "%'");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getFloorArea())) {
            query.append("\nand a.floorarea = " + buildingSearchRequest.getFloorArea());
        }


        if (!checkInputUtil.isNull(buildingSearchRequest.getWard())) {
            query.append("\nand a.ward like '%" + buildingSearchRequest.getWard() + "%' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStreet())) {
            query.append("\nand a.street like '%" + buildingSearchRequest.getStreet() + "%' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getNumberOfBasement())) {
            query.append("\nand a.numberofbasement = " + buildingSearchRequest.getNumberOfBasement() + " ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getDirection())) {
            query.append("\nand a.direction ='" + buildingSearchRequest.getDirection() + "' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getLevel())) {
            query.append("\nand a.level ='" + buildingSearchRequest.getLevel() + "' ");
        }


        if (!checkInputUtil.isNull(buildingSearchRequest.getRentPriceTo())) {
            query.append("\nand a.rentprice <= " + buildingSearchRequest.getRentPriceTo() + " ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentPriceFrom())) {
            query.append("\nand a.rentprice >= " + buildingSearchRequest.getRentPriceFrom() + " ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getManagerName())) {
            query.append("\nand a.managername like '%" + buildingSearchRequest.getManagerName() + "%'");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getManagerPhone())) {
            query.append("\nand a.managerphone like '%" + buildingSearchRequest.getManagerPhone() + "%'");
        }

        return query.toString();
    }
    public String buildQuery(BuildingSearchRequest buildingSearchRequest) {
        StringBuilder sql = new StringBuilder("select * from building as a ");
        sql.append(buildQueryWithJoin(buildingSearchRequest).get("join"));
        sql.append("where 1=1 ");
        sql.append(buildQueryWithJoin(buildingSearchRequest).get("query"));
        sql.append(buildQueryWithoutJoin(buildingSearchRequest));
        sql.append("\ngroup by a.id");
        System.out.println("===================================");
        System.out.println(sql.toString());
        return sql.toString();
    }
}
