package com.dominhtuan.api;

import com.dominhtuan.dto.BuildingDTO;
import com.dominhtuan.dto.request.BuildingSearchRequest;
import com.dominhtuan.dto.response.BuildingSearchResponse;
import com.dominhtuan.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;

    @GetMapping
    public List<BuildingSearchResponse> findBuilding(
            @RequestParam(required = false) Map<String, Object> params,
            @RequestParam(required = false) List<String> rentTypes
    ) throws SQLException {
        BuildingSearchRequest buildingSearchRequest = buildingService.getBuildingSearchRequest(params,rentTypes);
        List<BuildingSearchResponse> buildingResponses = buildingService.findBuilding(buildingSearchRequest);
        return buildingResponses;
    }

    @PostMapping
    public BuildingDTO createBuilding(@RequestBody BuildingDTO buildingDTO) {
        buildingService.save(buildingDTO);
        return buildingDTO;
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

