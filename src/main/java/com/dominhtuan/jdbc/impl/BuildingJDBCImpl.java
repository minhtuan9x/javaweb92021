package com.dominhtuan.jdbc.impl;

import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.entity.BuildingEntity;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.util.CheckInputUtil;
import com.dominhtuan.util.ConnectDBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
                rs = stmt.executeQuery(query(buildingSearchRequest));
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

    public String query(BuildingSearchRequest buildingSearchRequest) {
        StringBuilder sql = new StringBuilder("select * from building as a ");
        if (!checkInputUtil.isNull(buildingSearchRequest.getDistrictCode())) {
            sql.append("\ninner join district as b on a.districtid = b.id ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaFrom()) || !checkInputUtil.isNull(buildingSearchRequest.getRentAreaTo())) {
            sql.append("\ninner join rentarea as c on a.id = c.buildingid ");
        }
        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            sql.append("\ninner join buildingrenttype as d on a.id = d.buildingid \ninner join renttype as e on e.id = d.renttypeid ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStaffID())) {
            sql.append("\ninner join assignmentbuilding as f on a.id = f.buildingid inner join user as g on f.staffid = g.id ");
        }

        sql.append("where 1=1 ");

        if (!checkInputUtil.isNull(buildingSearchRequest.getBuildingName())) {
            sql.append("\nand a.name like '%" + buildingSearchRequest.getBuildingName() + "%'");
        }


        if (!checkInputUtil.isNull(buildingSearchRequest.getFloorArea())) {
            sql.append("\nand a.floorarea = " + buildingSearchRequest.getFloorArea());
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getDistrictCode())) {
            sql.append("\nand b.code ='" + buildingSearchRequest.getDistrictCode() + "' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getWard())) {
            sql.append("\nand a.ward like '%" + buildingSearchRequest.getWard() + "%' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStreet())) {
            sql.append("\nand a.street like '%" + buildingSearchRequest.getStreet() + "%' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getNumberOfBasement())) {
            sql.append("\nand a.numberofbasement = " + buildingSearchRequest.getNumberOfBasement() + " ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getDirection())) {
            sql.append("\nand a.direction ='" + buildingSearchRequest.getDirection() + "' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getLevel())) {
            sql.append("\nand a.level ='" + buildingSearchRequest.getLevel() + "' ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaTo())) {
            sql.append("\nand c.value <= " + buildingSearchRequest.getRentAreaTo() + " ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentAreaFrom())) {
            sql.append("\nand c.value >= " + buildingSearchRequest.getRentAreaFrom() + " ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getRentPriceTo())) {
            sql.append("\nand a.rentprice <= " + buildingSearchRequest.getRentPriceTo() + " ");
        }
        if (!checkInputUtil.isNull(buildingSearchRequest.getRentPriceFrom())) {
            sql.append("\nand a.rentprice >= " + buildingSearchRequest.getRentPriceFrom() + " ");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getManagerName())) {
            sql.append("\nand a.managername like '%" + buildingSearchRequest.getManagerName() + "%'");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getManagerPhone())) {
            sql.append("\nand a.managerphone like '%" + buildingSearchRequest.getManagerPhone() + "%'");
        }

        if (!checkInputUtil.isNull(buildingSearchRequest.getStaffID())) {
            sql.append("\nand g.id = " + buildingSearchRequest.getStaffID());
        }

        if (buildingSearchRequest.getRentTypes() != null && buildingSearchRequest.getRentTypes().size() > 0) {
            sql.append("\nand ( ");
            for (String item : buildingSearchRequest.getRentTypes()) {
                if (buildingSearchRequest.getRentTypes().indexOf(item) == buildingSearchRequest.getRentTypes().size() - 1) {
                    sql.append("e.code = '" + item + "' ");
                } else {
                    sql.append("e.code = '" + item + "' or ");
                }
            }
            sql.append(" )");
        }
        sql.append("\ngroup by a.id");
        System.out.println("===================================");
        System.out.println(sql.toString());
        return sql.toString();
    }
}
