package com.dominhtuan.service.impl;

import com.dominhtuan.convert.BuildingConverter;
import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.entity.BuildingEntity;
import com.dominhtuan.entity.DistrictEntity;
import com.dominhtuan.exception.YeuDiemPhucException;
import com.dominhtuan.jdbc.BuildingJDBC;
import com.dominhtuan.jdbc.DistrictJDBC;
import com.dominhtuan.service.BuildingService;
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
        for (BuildingEntity item : buildingJDBC.findBuilding(buildingSearchRequest)) {
            DistrictEntity districtEntity = districtJDBC.findDistrictByDistrictID(item.getDistrictID());
            String districtName = districtEntity.getName();
            BuildingSearchResponse buildingResponse = buildingConverter.buildingEntityToBuildingResponse(item, districtName);
            buildingResponses.add(buildingResponse);
        }
        return buildingResponses;
    }

    @Override
    public void save(BuildingDTO buildingDTO) {

    }

    @Override
    public BuildingSearchRequest getBuildingSearchRequest(Map<String, Object> params,List<String> rentTypes) {
        BuildingSearchRequest buildingSearchRequest = new BuildingSearchRequest();
        buildingSearchRequest.setBuildingName((String) params.get("name"));
        buildingSearchRequest.setFloorArea((Integer) params.get("floorArea"));
        buildingSearchRequest.setDistrictCode((String) params.get("districtCode"));
        buildingSearchRequest.setWard((String) params.get("ward"));
        buildingSearchRequest.setStreet((String) params.get("street"));
        buildingSearchRequest.setNumberOfBasement((Integer) params.get("numberOfBasement"));
        buildingSearchRequest.setDirection((String) params.get("direction"));
        buildingSearchRequest.setLevel((String) params.get("level"));
        buildingSearchRequest.setRentAreaFrom((Integer) params.get("rentAreaFrom"));
        buildingSearchRequest.setRentAreaTo((Integer) params.get("rentAreaTo"));
        buildingSearchRequest.setRentPriceFrom((Integer) params.get("rentPriceFrom"));
        buildingSearchRequest.setRentPriceTo((Integer) params.get("rentPriceTo"));
        buildingSearchRequest.setManagerName((String) params.get("managerName"));
        buildingSearchRequest.setManagerPhone((String) params.get("managerPhone"));
        buildingSearchRequest.setStaffID((Integer) params.get("staffID"));
        buildingSearchRequest.setRentTypes(rentTypes);
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
