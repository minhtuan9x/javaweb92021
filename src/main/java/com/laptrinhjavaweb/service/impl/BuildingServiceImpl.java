package com.laptrinhjavaweb.service.impl;
import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.converter.RentAreaConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.RentAreaDTO;
import com.laptrinhjavaweb.dto.UserDTO;
import com.laptrinhjavaweb.dto.request.AssignmentBuildingRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import com.laptrinhjavaweb.dto.response.StaffAssignmentResponse;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.exception.MyException;
import com.laptrinhjavaweb.repository.BuildingRepository;
import com.laptrinhjavaweb.repository.UserRepository;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.RentAreaService;
import com.laptrinhjavaweb.utils.MapUtil;
import com.laptrinhjavaweb.utils.ParseIntUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingConverter buildingConverter;
	@Autowired
	private RentAreaConverter rentAreaConverter;
	@Autowired
	private BuildingRepository buildingReporitory;
	@Autowired
	private RentAreaService rentAreaService;
	@Autowired
	private UserRepository userRepository;

	@Override
	public List<BuildingResponse> findAll(Map<String, Object> params, List<String> rentTypes) {
		
		List<BuildingResponse> buildingResponses = new ArrayList<>();
		BuildingSearchBuilder buildingSearchBuilder = toBuildingSearchBuilder(params, rentTypes);
		for (BuildingEntity item : buildingReporitory.findAll(buildingSearchBuilder)) {
			buildingResponses.add(buildingConverter.toBuildingResponse(item));
		}
		return buildingResponses;
	}

	@Override
	public List<BuildingResponse> findByNameLike(String name) {
		List<BuildingResponse> buildingResponses = new ArrayList<>();
		for(BuildingEntity item : buildingReporitory.findByNameContaining(name)){
			buildingResponses.add(buildingConverter.toBuildingResponse(item));
		}
		return buildingResponses;
	}

	@Override
	public BuildingDTO findById(Long id) {
		return id!=null?buildingConverter.toBuildingDTO(buildingReporitory.findById(id)):new BuildingDTO();
	}

	@Override
	public BuildingDTO save(BuildingDTO buildingDTO) {
		BuildingEntity buildingEntity = buildingConverter.toBuildingEntity(buildingDTO);
		try {
			BuildingEntity buildingEntityGetIDafterSave = buildingReporitory.save(buildingEntity);
			if(buildingDTO.getRentArea()!=null){
				List<RentAreaDTO> rentAreaDTOS = rentAreaConverter.toRentAreaDTOs(buildingEntityGetIDafterSave.getId(),buildingDTO);
				rentAreaService.saveAllByBuilding(rentAreaDTOS,buildingDTO);
			}
			return buildingConverter.toBuildingDTO(buildingEntityGetIDafterSave);
		}catch (Exception e){
			System.out.println("Error BuildingService");
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public void assignmentBuilding(AssignmentBuildingRequest assignmentBuildingRequest, Long buildingID) {
		List<UserEntity> userEntities = new ArrayList<>();
		for(Integer item : assignmentBuildingRequest.getStaffIDs()){
			userEntities.add(userRepository.findOneById(item.longValue()));
		}
		BuildingEntity buildingEntity = buildingReporitory.findById(buildingID);
		buildingReporitory.assignmentBuilding(userEntities,buildingEntity);
	}

	@Override
	public void delete(Long id) {
		BuildingEntity buildingEntity = buildingReporitory.findById(id);
		if(buildingEntity!=null){
			buildingReporitory.deleteBuilding(buildingEntity);
		}
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
		}catch (MyException e){
			throw e;
		}

	}
}
