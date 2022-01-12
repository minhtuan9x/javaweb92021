package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.constant.SystemConstant;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.converter.RentAreaConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.RentAreaDTO;
import com.laptrinhjavaweb.dto.request.BuildingDelRequest;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.exception.MyException;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.RentAreaRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.RentAreaService;
import com.laptrinhjavaweb.utils.MapUtil;
import com.laptrinhjavaweb.utils.ParseIntUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingConverter buildingConverter;
    @Autowired
    private RentAreaConverter rentAreaConverter;
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private RentAreaService rentAreaService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RentAreaRepository rentAreaRepository;


    @Override
    public List<BuildingResponse> findAll(Map<String, Object> params, List<String> rentTypes) {

        List<BuildingResponse> buildingResponses = new ArrayList<>();
        BuildingSearchBuilder buildingSearchBuilder = toBuildingSearchBuilder(params, rentTypes);
        for (BuildingEntity item : buildingRepository.findAll(buildingSearchBuilder)) {
            buildingResponses.add(buildingConverter.toBuildingResponse(item));
        }
        return buildingResponses;
    }

    @Override
    public List<BuildingResponse> findAll(BuildingSearchRequest buildingSearchRequest) {
        List<BuildingResponse> buildingResponses = new ArrayList<>();
        BuildingSearchBuilder buildingSearchBuilder = toBuildingSearchBuilder(buildingSearchRequest);
        for (BuildingEntity item : buildingRepository.findAll(buildingSearchBuilder)) {
            buildingResponses.add(buildingConverter.toBuildingResponse(item));
        }
        return buildingResponses;
    }

    @Override
    public BuildingDTO findById(Long id) {
        return id != null ? buildingConverter.toBuildingDTO(buildingRepository.findById(id)) : new BuildingDTO();
    }

    @Override
    public BuildingDTO save(BuildingDTO buildingDTO) {
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        try {
            BuildingEntity buildingEntityGetIDafterSave = buildingRepository.save(buildingEntity);
            if (buildingDTO.getRentArea() != null) {
                List<RentAreaDTO> rentAreaDTOS = rentAreaConverter.toRentAreaDTOs(buildingEntityGetIDafterSave.getId(), buildingDTO);
                rentAreaService.saveAllByBuilding(rentAreaDTOS, buildingDTO);
            }
            return buildingConverter.toBuildingDTO(buildingEntityGetIDafterSave);
        } catch (Exception e) {
            System.out.println("Error BuildingService");
            e.printStackTrace();
            return null;
        }
    }
    @Override
    @Transactional
    public void assignmentBuilding(List<Long> staffIds, Long buildingID) throws NotFoundException {
        try {
            BuildingEntity buildingEntity = buildingRepository.findOne(buildingID);
            buildingEntity.setUserEntities(new HashSet<>(Optional.ofNullable(userRepository.findAll(staffIds))
                    .orElseThrow(()->new NotFoundException(SystemConstant.NF_USER))));
            buildingRepository.save(buildingEntity);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    @Transactional
    public void deleteWithCascade(BuildingDelRequest buildingDelRequest) {
        if(!buildingDelRequest.getBuildingIds().isEmpty()){
            buildingRepository.deleteByIdIn(buildingDelRequest.getBuildingIds());
        }
    }

    @Transactional
    @Override
    public BuildingDTO savePart2(BuildingDTO buildingDTO) throws NotFoundException {
        Long buildingId = buildingDTO.getId();
        if (Objects.nonNull(buildingDTO)) {//objects java 7, check != null
            BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
            if (Objects.nonNull(buildingId) && buildingId > 0) {//id != null update
                BuildingEntity buildingEntityFound = Optional.ofNullable(buildingRepository.findOne(buildingId))
                        .orElseThrow(() -> new NotFoundException(SystemConstant.NF_BUILDING));
                //Optional.ofNullable neu != null thi tra ve gia tri, null thi tra ve exception
                buildingEntity.setCreatedBy(buildingEntityFound.getCreatedBy());
                buildingEntity.setCreatedDate(buildingEntityFound.getCreatedDate());
                if (!rentAreaIsPresent(buildingEntityFound, buildingDTO)) {
                    rentAreaRepository.deleteByBuildingEntity_Id(buildingId);//xoa tat ca rent area cua building, de them lai
                } else {
                    buildingEntity.setRentAreaEntities(new ArrayList<>());
                }
            }
            BuildingDTO savedBuilding = buildingConverter.toBuildingDTO(buildingRepository.save(buildingEntity));
            rentAreaRepository.save(buildingEntity.getRentAreaEntities());//insert lai rent area
            return savedBuilding;
        }
        return null;
    }

    @Override
    @Transactional
    public BuildingDTO saveWithCascade(BuildingDTO buildingDTO) {
        if (buildingDTO.getId() != null) {
            rentAreaRepository.deleteByBuildingEntity_Id(buildingDTO.getId());
        }
        BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
        return buildingConverter.toBuildingDTO( buildingRepository.save(buildingEntity));
    }

    private Boolean rentAreaIsPresent(BuildingEntity buildingEntity, BuildingDTO buildingDTO) {
        List<String> valueAreas = new ArrayList<>();
        buildingEntity.getRentAreaEntities().forEach(item -> {
            valueAreas.add(String.valueOf(item.getValue()));
        });
        String rentAreaStrOut = String.join(",", valueAreas), rentAreaStrIn = buildingDTO.getRentArea();
        return rentAreaStrIn.equals(rentAreaStrOut);
    }

    private BuildingSearchBuilder toBuildingSearchBuilder(Map<String, Object> params, List<String> rentTypes) {
        try {
            Map<String, Object> paramsPsd = toLowKey(params);
            BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                    .name((String) MapUtil.getValue(paramsPsd, "name"))
                    .floorArea(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "floorarea")))
                    .district((String) MapUtil.getValue(paramsPsd, "districtcode"))
                    .ward((String) MapUtil.getValue(paramsPsd, "ward"))
                    .street((String) MapUtil.getValue(paramsPsd, "street"))
                    .numberOfBasement(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "numberofbasement")))
                    .direction((String) MapUtil.getValue(paramsPsd, "direction"))
                    .level((String) MapUtil.getValue(paramsPsd, "level"))
                    .rentAreaFrom(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "rentareafrom")))
                    .rentAreaTo(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "rentareato")))
                    .rentPriceFrom(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "rentpricefrom")))
                    .rentPriceTo(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "rentpriceto")))
                    .managerName((String) MapUtil.getValue(paramsPsd, "managername"))
                    .managerPhone((String) MapUtil.getValue(paramsPsd, "managerphone"))
                    .staffID(ParseIntUtil.getValue(MapUtil.getValue(paramsPsd, "staffid"))).rentTypes(rentTypes)
                    .build();
            return buildingSearchBuilder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private BuildingSearchBuilder toBuildingSearchBuilder(BuildingSearchRequest buildingSearchRequest) {
        try {
            BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                    .name(buildingSearchRequest.getName())
                    .floorArea(buildingSearchRequest.getFloorArea())
                    .district(buildingSearchRequest.getDistrictCode())
                    .ward(buildingSearchRequest.getWard())
                    .street(buildingSearchRequest.getStreet())
                    .numberOfBasement(buildingSearchRequest.getNumberOfBasement())
                    .direction(buildingSearchRequest.getDirection())
                    .level(buildingSearchRequest.getLevel())
                    .rentAreaFrom(buildingSearchRequest.getRentAreaFrom())
                    .rentAreaTo(buildingSearchRequest.getRentAreaTo())
                    .rentPriceFrom(buildingSearchRequest.getRentPriceFrom())
                    .rentPriceTo(buildingSearchRequest.getRentPriceTo())
                    .managerName(buildingSearchRequest.getManagerName())
                    .managerPhone(buildingSearchRequest.getManagerPhone())
                    .staffID(buildingSearchRequest.getStaffID())
                    .rentTypes(buildingSearchRequest.getRentTypes())
                    .build();
            return buildingSearchBuilder;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Map<String, Object> toLowKey(Map<String, Object> params) {
        Map<String, Object> results = new HashMap<>();
        for (Map.Entry<String, Object> item : params.entrySet()) {
            results.put(item.getKey().toLowerCase(), item.getValue());
        }
        return results;
    }

    public void validate(int a) throws MyException {
        try {
            if (a == 1)
                throw new MyException("loi test");
        } catch (MyException e) {
            throw e;
        }

    }
}
