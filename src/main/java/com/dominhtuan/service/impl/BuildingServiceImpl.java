package com.dominhtuan.service.impl;

import com.dominhtuan.convert.BuildingConverter;
import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.entity.DistrictEntity;
import com.dominhtuan.exception.YeuDiemPhucException;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.jdbc.DistrictJDBC;
import com.dominhtuan.service.BuildingService;
import com.dominhtuan.util.ParseValidateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
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
    public List<BuildingSearchResponse> findBuilding(BuildingSearchRequest buildingSearchRequest) throws SQLException {
        List<BuildingSearchResponse> buildingResponses = new ArrayList<>();
        validateNameInput(buildingSearchRequest);
        buildingJDBC.findBuilding(buildingSearchRequest).forEach(item ->{
            DistrictEntity districtEntity = null;
            try {
                districtEntity = districtJDBC.findDistrictByDistrictID(item.getDistrictID());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String districtName = districtEntity.getName();
            BuildingSearchResponse buildingResponse = buildingConverter.buildingEntityToBuildingResponse(item, districtName);
            buildingResponses.add(buildingResponse);
        });

//        for (BuildingEntity item : buildingJDBC.findBuilding(buildingSearchRequest)) {
//            DistrictEntity districtEntity = districtJDBC.findDistrictByDistrictID(item.getDistrictID());
//            String districtName = districtEntity.getName();
//            BuildingSearchResponse buildingResponse = buildingConverter.buildingEntityToBuildingResponse(item, districtName);
//            buildingResponses.add(buildingResponse);
//        }
//
        return buildingResponses;
    }

    @Override
    public void save(BuildingDTO buildingDTO) {

    }

    @Override
    public BuildingSearchRequest getBuildingSearchRequest(Map<String, Object> params, List<String> rentTypes) {
        BuildingSearchRequest buildingSearchRequest = new BuildingSearchRequest();
        try {
            buildingSearchRequest.setBuildingName((String) ParseValidateUtil.parseValidate(params.get("name")));
            buildingSearchRequest.setDistrictCode((String) ParseValidateUtil.parseValidate(params.get("districtCode")));
            buildingSearchRequest.setWard((String) ParseValidateUtil.parseValidate(params.get("ward")));
            buildingSearchRequest.setStreet((String) ParseValidateUtil.parseValidate(params.get("street")));
            buildingSearchRequest.setDirection((String) ParseValidateUtil.parseValidate(params.get("direction")));
            buildingSearchRequest.setLevel((String) ParseValidateUtil.parseValidate(params.get("level")));
            buildingSearchRequest.setManagerName((String) ParseValidateUtil.parseValidate(params.get("managerName")));
            buildingSearchRequest.setManagerPhone((String) ParseValidateUtil.parseValidate(params.get("managerPhone")));

            buildingSearchRequest.setRentTypes(rentTypes);

            buildingSearchRequest.setFloorArea((Integer) ParseValidateUtil.parseValidate(params.get("floorArea")));
            buildingSearchRequest.setNumberOfBasement((Integer) ParseValidateUtil.parseValidate(params.get("numberOfBasement")));
            buildingSearchRequest.setRentAreaFrom((Integer) ParseValidateUtil.parseValidate(params.get("rentAreaFrom")));
            buildingSearchRequest.setRentAreaTo((Integer) ParseValidateUtil.parseValidate(params.get("rentAreaTo")));
            buildingSearchRequest.setRentPriceFrom((Integer) ParseValidateUtil.parseValidate(params.get("rentPriceFrom")));
            buildingSearchRequest.setRentPriceTo((Integer) ParseValidateUtil.parseValidate(params.get("rentPriceTo")));
            buildingSearchRequest.setStaffID((Integer) ParseValidateUtil.parseValidate(params.get("staffID")));
        } catch (Exception e) {
            System.out.println("Loi getBuildingSearchRequest");
            e.printStackTrace();
        }
        return buildingSearchRequest;
    }


    private void validateNameInput(BuildingSearchRequest buildingSearchRequest) {
        try {
            if (buildingSearchRequest.getBuildingName() != null) {
                if (buildingSearchRequest.getBuildingName().equals("yeu diem phuc")) {
                    throw new YeuDiemPhucException("Ahihi đồ ngốc");
                }
            }
        } catch (YeuDiemPhucException e) {
            throw e;
        }

    }

}
