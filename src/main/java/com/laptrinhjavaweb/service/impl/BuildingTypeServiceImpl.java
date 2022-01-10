package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.response.BuildingTypeResponse;
import com.laptrinhjavaweb.enums.BuildingTypeEnum;
import com.laptrinhjavaweb.service.BuildingTypeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BuildingTypeServiceImpl implements BuildingTypeService {
    @Override
    public List<BuildingTypeResponse> getAll() {
        List<BuildingTypeResponse> buildingTypeDTOS = new ArrayList<>();
        for(BuildingTypeEnum item : BuildingTypeEnum.values()){
            BuildingTypeResponse buildingTypeResponse = new BuildingTypeResponse();
            buildingTypeResponse.setCode(item.toString());
            buildingTypeResponse.setName(item.getBuildingTypeValue());
            buildingTypeDTOS.add(buildingTypeResponse);
        }
        return buildingTypeDTOS;
    }

    @Override
    public List<BuildingTypeResponse> getAllByBuilding(BuildingDTO buildingDTO) {
        List<BuildingTypeResponse> buildingTypeResponses = new ArrayList<>();
        for(BuildingTypeEnum item :BuildingTypeEnum.values()){
            BuildingTypeResponse buildingTypeResponse = new BuildingTypeResponse();
            buildingTypeResponse.setCode(item.toString());
            buildingTypeResponse.setName(item.getBuildingTypeValue());
            if(buildingDTO.getType()!=null){
                for(String item2: buildingDTO.getType()){
                    if(item2.equals(item.name())){
                        buildingTypeResponse.setChecked("checked");
                    }
                }
            }
            buildingTypeResponses.add(buildingTypeResponse);
        }
        return buildingTypeResponses;
    }
}
