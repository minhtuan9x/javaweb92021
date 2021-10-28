package com.dominhtuan.service;

import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.response.BuildingSearchResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<BuildingSearchResponse> findBuilding(Map<String, Object> params, List<String> rentTypes) throws SQLException;
    void save(BuildingDTO buildingDTO);
}
