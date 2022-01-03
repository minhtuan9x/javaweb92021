package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.RentAreaDTO;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.RentAreaEntity;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.utils.ParseIntUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RentAreaConverter {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private BuildingConverter buildingConverter;



    public RentAreaEntity toRentAreaEntity(RentAreaDTO rentAreaDTO) {
        RentAreaEntity rentAreaEntity = modelMapper.map(rentAreaDTO, RentAreaEntity.class);
        rentAreaEntity.setBuildingEntity(buildingRepository.findById(rentAreaDTO.getBuildingID()));
        return rentAreaEntity;
    }

    public List<RentAreaDTO> toRentAreaDTOs(Long buildingIDAfterSave, BuildingDTO buildingDTO) {
        List<RentAreaDTO> rentAreaDTOS = new ArrayList<>();
        BuildingDTO buildingDTOGetRentArea = buildingConverter.toBuildingDTO(buildingRepository.findById(buildingIDAfterSave));
        if(buildingDTOGetRentArea.getRentArea().equals(buildingDTO.getRentArea()))
            return new ArrayList<>();
        String[] rentArea = buildingDTO.getRentArea() != null ? buildingDTO.getRentArea().trim().split(",") : null;
        if (rentArea != null) {
            for (String item : rentArea) {
                RentAreaDTO rentAreaDTO = new RentAreaDTO();
                rentAreaDTO.setBuildingID(buildingIDAfterSave);
                rentAreaDTO.setValue(ParseIntUtil.getValue(item));
                rentAreaDTOS.add(rentAreaDTO);
            }
            return rentAreaDTOS;
        } else
            return new ArrayList<>();
    }
}
