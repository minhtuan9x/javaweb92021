package com.dominhtuan.convert;

import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.entity.BuildingEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildingConverter {

    @Autowired
    private ModelMapper modelMapper;

    public BuildingSearchResponse buildingEntityToBuildingResponse(BuildingEntity buildingEntity, String districtName){
        BuildingSearchResponse buildingResponse = modelMapper.map(buildingEntity,BuildingSearchResponse.class);
        buildingResponse.setAddress(buildingEntity.getStreet()+"-"+buildingEntity.getWard()+"-"+districtName);
        return buildingResponse;
    }
}
