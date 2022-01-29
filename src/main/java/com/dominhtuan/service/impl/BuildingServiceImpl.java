package com.dominhtuan.service.impl;

import com.dominhtuan.convert.BuildingConverter;
import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.entity.BuildingEntity;
import com.dominhtuan.entity.DistrictEntity;
import com.dominhtuan.exception.MyException;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.jdbc.DistrictJDBC;
import com.dominhtuan.service.BuildingService;
import com.dominhtuan.util.ParseValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService {
	@Autowired
	private BuildingJDBC buildingJDBC;
	@Autowired
	private DistrictJDBC districtJDBC;
	@Autowired
	private BuildingConverter buildingConverter;

	@Override
	public List<BuildingSearchResponse> findBuilding(Map<String, Object> params, List<String> rentTypes)
			throws SQLException {
		List<BuildingSearchResponse> buildingResponses = new ArrayList<>();
		BuildingSearchRequest buildingSearchRequest = getBuildingSearchRequest(params, rentTypes);
		validateNameInput(buildingSearchRequest);

		for (BuildingEntity item : buildingJDBC.findBuilding(buildingSearchRequest)) {
			DistrictEntity districtEntity = districtJDBC.findByDistrictID(item.getDistrictID());
			String districtName = districtEntity.getName();
			BuildingSearchResponse buildingResponse = buildingConverter.buildingEntityToBuildingResponse(item,
					districtName);
			buildingResponses.add(buildingResponse);
		}
		return buildingResponses;
	}

	@Override
	public void save(BuildingDTO buildingDTO) {

	}

	public BuildingSearchRequest getBuildingSearchRequest(Map<String, Object> params1, List<String> rentTypes) {
		BuildingSearchRequest buildingSearchRequest = new BuildingSearchRequest();
		Map<String, Object> params = toLowKey(params1);
		try {
			buildingSearchRequest.setBuildingName((String) ParseValidateUtil.parseValidate(params.get("name")));
			buildingSearchRequest.setDistrictCode((String) ParseValidateUtil.parseValidate(params.get("districtcode")));
			buildingSearchRequest.setWard((String) ParseValidateUtil.parseValidate(params.get("ward")));
			buildingSearchRequest.setStreet((String) ParseValidateUtil.parseValidate(params.get("street")));
			buildingSearchRequest.setDirection((String) ParseValidateUtil.parseValidate(params.get("direction")));
			buildingSearchRequest.setLevel((String) ParseValidateUtil.parseValidate(params.get("level")));
			buildingSearchRequest.setManagerName((String) ParseValidateUtil.parseValidate(params.get("managername")));
			buildingSearchRequest.setManagerPhone((String) ParseValidateUtil.parseValidate(params.get("managerphone")));

			System.out.println(rentTypes);
			buildingSearchRequest.setRentTypes(rentTypes);

			buildingSearchRequest.setFloorArea((Integer) ParseValidateUtil.parseValidate(params.get("floorarea")));
			buildingSearchRequest.setNumberOfBasement((Integer) ParseValidateUtil.parseValidate(params.get("numberofbasement")));
			buildingSearchRequest.setRentAreaFrom((Integer) ParseValidateUtil.parseValidate(params.get("rentareafrom")));
			buildingSearchRequest.setRentAreaTo((Integer) ParseValidateUtil.parseValidate(params.get("rentareato")));
			buildingSearchRequest.setRentPriceFrom((Integer) ParseValidateUtil.parseValidate(params.get("rentpricefrom")));
			buildingSearchRequest.setRentPriceTo((Integer) ParseValidateUtil.parseValidate(params.get("rentpriceto")));
			buildingSearchRequest.setStaffID((Integer) ParseValidateUtil.parseValidate(params.get("staffid")));
		} catch (Exception e) {
			System.out.println("Loi getBuildingSearchRequest");
			e.printStackTrace();
		}
		return buildingSearchRequest;
	}
	private Map<String, Object> toLowKey(Map<String, Object> params){
		Map<String, Object> results = new HashMap<>();
		for(Map.Entry<String,Object> item : params.entrySet()){
			results.put(item.getKey().toLowerCase(),item.getValue());
		}
		return results;
	}
	private void validateNameInput(BuildingSearchRequest buildingSearchRequest) {
		try {
			if (buildingSearchRequest.getBuildingName() != null) {
				if (buildingSearchRequest.getBuildingName().equals("yeu diem phuc")) {
					throw new MyException("Ahihi đồ ngốc");
				}
			}
		} catch (MyException e) {
			throw e;
		}

	}

}
