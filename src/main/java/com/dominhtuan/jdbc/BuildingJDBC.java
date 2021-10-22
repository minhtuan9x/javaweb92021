package com.dominhtuan.jdbc;

import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.entity.BuildingEntity;

import java.sql.SQLException;
import java.util.List;

public interface BuildingJDBC {
    List<BuildingEntity> findBuilding(BuildingSearchRequest buildingSearchRequest) throws SQLException;
}
