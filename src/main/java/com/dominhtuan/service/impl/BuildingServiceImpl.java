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
    public BuildingSearchRequest getBuildingSearchRequest(Map<String, String> params,List<String> rentTypes) {
        BuildingSearchRequest buildingSearchRequest = new BuildingSearchRequest();
        try{
            buildingSearchRequest.setBuildingName(params.get("name"));
            buildingSearchRequest.setDistrictCode(params.get("districtCode"));
            buildingSearchRequest.setWard(params.get("ward"));
            buildingSearchRequest.setStreet( params.get("street"));
            buildingSearchRequest.setDirection(params.get("direction"));
            buildingSearchRequest.setLevel(params.get("level"));
            buildingSearchRequest.setManagerName(params.get("managerName"));
            buildingSearchRequest.setManagerPhone(params.get("managerPhone"));
            buildingSearchRequest.setRentTypes(rentTypes);
            if(params.get("floorArea")!=null)
                buildingSearchRequest.setFloorArea(Integer.valueOf(params.get("floorArea")));
            if(params.get("numberOfBasement")!=null)
                buildingSearchRequest.setNumberOfBasement(Integer.valueOf(params.get("numberOfBasement")));
            if(params.get("rentAreaFrom")!=null)
                buildingSearchRequest.setRentAreaFrom(Integer.valueOf(params.get("rentAreaFrom")));
            if(params.get("rentAreaTo")!=null)
                buildingSearchRequest.setRentAreaTo(Integer.valueOf(params.get("rentAreaTo")));
            if(params.get("rentPriceFrom")!=null)
                buildingSearchRequest.setRentPriceFrom(Integer.valueOf(params.get("rentPriceFrom")));
            if(params.get("rentPriceTo")!=null)
                buildingSearchRequest.setRentPriceTo(Integer.valueOf(params.get("rentPriceTo")));
            if(params.get("staffID")!=null)
                buildingSearchRequest.setStaffID(Integer.valueOf(params.get("staffID")));
        }catch (Exception e){
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
