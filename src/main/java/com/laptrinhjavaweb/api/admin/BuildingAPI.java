package com.laptrinhjavaweb.api.admin;


import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.AssignmentBuildingRequest;
import com.laptrinhjavaweb.dto.request.BuildingDelRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import com.laptrinhjavaweb.dto.response.StaffAssignmentResponse;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.impl.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/building")
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private UserService userService;

    @GetMapping
    public List<BuildingResponse> findAll(@RequestParam(required = false) Map<String, Object> params,
                                          @RequestParam(name = "renttypes", required = false) List<String> rentTypes) {
        return buildingService.findAll(params, rentTypes);
    }

    @GetMapping("/name")
    public List<BuildingResponse> findByNameLike(@RequestParam(required = false, name = "name") String name) {
        return buildingService.findByNameLike(name);
    }

    @PostMapping
    public BuildingDTO save(@RequestBody(required = false) BuildingDTO buildingDTO) {
        return buildingService.save(buildingDTO);
    }

    @GetMapping("/{id}/staff")
    public List<StaffAssignmentResponse> getAllStaffByBuilding(@PathVariable("id") Long id) {
        return userService.getAllStaffAssignmentBuilding(id);
    }

    @PostMapping("/{id}/assignment")
    public AssignmentBuildingRequest assignmentBuilding(@RequestBody(required = false) AssignmentBuildingRequest assignmentBuildingRequest
            , @PathVariable("id") Long buildingId) {
        buildingService.assignmentBuilding(assignmentBuildingRequest, buildingId);
        return assignmentBuildingRequest;
    }

    @DeleteMapping
    public BuildingDelRequest delete(@RequestBody BuildingDelRequest buildingDelRequest) throws NotFoundException {
        buildingService.deleteWithCascade(buildingDelRequest);
        return buildingDelRequest;
    }
    @PostMapping("/savepart2")
    public BuildingDTO savePart2(@RequestBody BuildingDTO buildingDTO) throws NotFoundException {
        return buildingService.savePart2(buildingDTO);
    }
}
