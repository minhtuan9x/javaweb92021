package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.AssignmentBuildingRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<BuildingResponse> findAll(Map<String, Object> params, List<String> rentTypes);
    List<BuildingResponse> findByNameLike(String name);
    BuildingDTO findById(Long id);
    BuildingDTO save(BuildingDTO buildingDTO);
    void assignmentBuilding(AssignmentBuildingRequest assignmentBuildingRequest, Long buildingID);
    void delete(Long id);
}
