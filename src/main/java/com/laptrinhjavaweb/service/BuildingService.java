package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.AssignmentBuildingRequest;
import com.laptrinhjavaweb.dto.request.BuildingDelRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import javassist.NotFoundException;

import java.util.List;
import java.util.Map;

public interface BuildingService {
    List<BuildingResponse> findAll(Map<String, Object> params, List<String> rentTypes);
    BuildingDTO findById(Long id);
    BuildingDTO save(BuildingDTO buildingDTO);
    void assignmentBuilding(AssignmentBuildingRequest assignmentBuildingRequest, Long buildingID);
    void delete(BuildingDelRequest buildingDelRequest) throws NotFoundException;
    void deleteWithCascade(BuildingDelRequest buildingDelRequest);
    BuildingDTO savePart2(BuildingDTO buildingDTO) throws NotFoundException;
    BuildingDTO saveWithCascade(BuildingDTO buildingDTO);
}
