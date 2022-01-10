package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingTypeResponse;

import java.util.List;

public interface BuildingTypeService {
    List<BuildingTypeResponse> getAll();
    List<BuildingTypeResponse> getAllByBuilding(BuildingDTO buildingDTO);
}
