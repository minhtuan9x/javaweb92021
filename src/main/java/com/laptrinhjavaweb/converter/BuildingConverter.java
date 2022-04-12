package com.laptrinhjavaweb.converter;


import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.enums.DistrictEnum;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.service.DistrictService;
import com.laptrinhjavaweb.utils.ParseIntUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class BuildingConverter {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserConverter userConverter;

    public BuildingResponse toBuildingResponse(BuildingEntity buildingEntity) {
        BuildingResponse buildingResponse;
        buildingResponse = modelMapper.map(buildingEntity, BuildingResponse.class);
        String districtName = "";
        for (DistrictEnum item : DistrictEnum.values()) {
            if (item.name().equals(buildingEntity.getDistrict())) {
                districtName = item.getDistrictValue();
                break;
            }
        }
        buildingResponse.setAddress(buildingEntity.getStreet() + "-" + buildingEntity.getWard() + "-" + districtName);
        return buildingResponse;
    }

    public BuildingDTO toBuildingDTO(BuildingEntity buildingEntity) {
        BuildingDTO buildingDTO = modelMapper.map(buildingEntity, BuildingDTO.class);
        List<String> rentAreas = new ArrayList<>();
        for (RentAreaEntity item : buildingEntity.getRentAreaEntities()) {
            rentAreas.add(String.valueOf(item.getValue()));
        }
        String rentAreaStr = String.join(",", rentAreas);
        buildingDTO.setRentArea(rentAreaStr);
        if (buildingEntity.getType() != null) {
            List<String> typeDTOs = new ArrayList<>();
            String[] types = buildingEntity.getType().trim().split(",");
            for (String item : types) {
                typeDTOs.add(item);
            }
            buildingDTO.setType(typeDTOs);
        }
        List<UserDTO> userDTOS = new ArrayList<>();
        if (buildingEntity.getUserEntities().size() > 0) {
            buildingEntity.getUserEntities().forEach(item -> {
                userDTOS.add(userConverter.convertToDto(item));
            });
            buildingDTO.setUserDTOS(userDTOS);
        }
        return buildingDTO;
    }

    public BuildingSearchRequest toBuildingSearchRequest(BuildingSearchRequest buildingSearchRequest) {
        if (buildingSearchRequest.getRentTypes() != null) {
            List<String> a = new ArrayList<>();
            for (String item : buildingSearchRequest.getRentTypes()) {
                a.add("'" + item + "'");
            }
            buildingSearchRequest.setRentTypes(a);
        }
        return buildingSearchRequest;
    }

    public BuildingEntity toBuildingEntity(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntity = modelMapper.map(buildingDTO, BuildingEntity.class);
        if (buildingDTO.getType() != null) {
            String type = String.join(",", buildingDTO.getType());
            buildingEntity.setType(type);
        }
        if (buildingDTO.getRentArea() != null) {
            List<RentAreaEntity> rentAreaEntityNews = new ArrayList<>();
            String[] rentAreaValues = buildingDTO.getRentArea().trim().split(",");
            for (String item : rentAreaValues) {
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                rentAreaEntity.setBuildingEntity(buildingEntity);
                rentAreaEntity.setValue(ParseIntUtil.getValue(item));
                rentAreaEntityNews.add(rentAreaEntity);
            }
            buildingEntity.setRentAreaEntities(rentAreaEntityNews);
        }
        return buildingEntity;
    }
}
