package com.dominhtuan.service;

import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<BuildingSearchResponse> findBuilding(BuildingSearchRequest buildingSearchRequest) throws SQLException;
    void save(BuildingDTO buildingDTO);
    BuildingSearchRequest getBuildingSearchRequest(Map<String,Object> params);
}
