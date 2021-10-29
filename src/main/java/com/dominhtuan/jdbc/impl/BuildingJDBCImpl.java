package com.dominhtuan.jdbc.impl;

import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.entity.BuildingEntity;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.util.ConnectDBUtil;
import com.dominhtuan.util.SqlUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
        SqlUtil.buildQueryUsingBetween("ra.value",buildingSearchRequest.getRentAreaFrom(), buildingSearchRequest.getRentAreaTo(), where,join,
                "\ninner join rentarea as ra on bd.id = ra.buildingid ");
        SqlUtil.buildQueryUsingOperator("dt.code","=",buildingSearchRequest.getDistrictCode(),where);
        SqlUtil.buildQueryUsingOperator("u.id","=",buildingSearchRequest.getStaffID(),where,join,
                "\ninner join assignmentbuilding as ab on bd.id = ab.buildingid inner join user as u on ab.staffid = u.id ");
        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            join.append("\ninner join buildingrenttype as br on bd.id = br.buildingid \ninner join renttype as rt on rt.id = br.renttypeid ");
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
        SqlUtil.buildQueryUsingLike("bd.name", buildingSearchRequest.getBuildingName(), where);
        SqlUtil.buildQueryUsingOperator("bd.floorarea", "=", buildingSearchRequest.getFloorArea(), where);
        SqlUtil.buildQueryUsingLike("bd.ward", buildingSearchRequest.getWard(), where);
        SqlUtil.buildQueryUsingLike("bd.street", buildingSearchRequest.getStreet(), where);
        SqlUtil.buildQueryUsingOperator("bd.numberofbasement", "=", buildingSearchRequest.getNumberOfBasement(), where);
        SqlUtil.buildQueryUsingOperator("bd.direction", "=", buildingSearchRequest.getDirection(), where);
        SqlUtil.buildQueryUsingOperator("bd.level", "=", buildingSearchRequest.getLevel(), where);
        SqlUtil.buildQueryUsingLike("bd.managername", buildingSearchRequest.getManagerName(), where);
        SqlUtil.buildQueryUsingLike("bd.managerphone", buildingSearchRequest.getManagerPhone(), where);
        SqlUtil.buildQueryUsingBetween("bd.rentprice",buildingSearchRequest.getRentPriceFrom(), buildingSearchRequest.getRentPriceTo(),where);
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
