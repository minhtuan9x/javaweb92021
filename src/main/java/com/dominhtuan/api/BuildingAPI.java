package com.dominhtuan.api;

import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;


    @PostMapping
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
        buildingService.save(buildingDTO);
        return buildingDTO;
    }


    @GetMapping
    public List<BuildingSearchResponse> findBuilding(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "floorArea", required = false) Integer floorArea,
            @RequestParam(value = "districtCode", required = false) String districtCode,
            @RequestParam(value = "ward", required = false) String ward,
            @RequestParam(value = "street", required = false) String street,
            @RequestParam(value = "numberOfBasement", required = false) Integer numberOfBasement,
            @RequestParam(value = "direction", required = false) String direction,
            @RequestParam(value = "level", required = false) String level,
            @RequestParam(value = "rentAreaFrom", required = false) Integer rentAreaFrom,
            @RequestParam(value = "rentAreaTo", required = false) Integer rentAreaTo,
            @RequestParam(value = "rentPriceFrom", required = false) Integer rentPriceFrom,
            @RequestParam(value = "rentPriceTo", required = false) Integer rentPriceTo,
            @RequestParam(value = "managerName", required = false) String managerName,
            @RequestParam(value = "managerPhone", required = false) String managerPhone,
            @RequestParam(value = "staffID", required = false) Integer staffID,
            @RequestParam(value = "rentTypes", required = false) List<String> rentTypes
    ) throws SQLException {
        BuildingSearchRequest buildingSearchRequest = new BuildingSearchRequest();
        buildingSearchRequest.setBuildingName(name);
        buildingSearchRequest.setFloorArea(floorArea);
        buildingSearchRequest.setDistrictCode(districtCode);
        buildingSearchRequest.setWard(ward);
        buildingSearchRequest.setStreet(street);
        buildingSearchRequest.setNumberOfBasement(numberOfBasement);
        buildingSearchRequest.setDirection(direction);
        buildingSearchRequest.setLevel(level);
        buildingSearchRequest.setRentAreaFrom(rentAreaFrom);
        buildingSearchRequest.setRentAreaTo(rentAreaTo);
        buildingSearchRequest.setRentPriceFrom(rentPriceFrom);
        buildingSearchRequest.setRentPriceTo(rentPriceTo);
        buildingSearchRequest.setManagerName(managerName);
        buildingSearchRequest.setManagerPhone(managerPhone);
        buildingSearchRequest.setStaffID(staffID);
        buildingSearchRequest.setRentTypes(rentTypes);
        List<BuildingSearchResponse> buildingResponses = buildingService.findBuilding(buildingSearchRequest);
        return buildingResponses;
    }

//    @GetMapping("/{id}")
//    public BuildingDTO getDetail(@PathVariable String id) {
//        List<BuildingDTO> buildingDTOS = createListBuilding();
//        for (BuildingDTO item : buildingDTOS) {
//            if (item.getName().equals(id)) {
//                return item;
//            }
//        }
//        return null;
//    }
}

