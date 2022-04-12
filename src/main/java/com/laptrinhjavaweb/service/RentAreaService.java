package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.RentAreaDTO;

import java.util.List;

public interface RentAreaService {
    void saveAllByBuilding(List<RentAreaDTO> rentAreaDTOS, BuildingDTO buildingDTO);
}
