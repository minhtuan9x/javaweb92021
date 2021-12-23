package com.laptrinhjavaweb.api.admin;


import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.AssignmentBuildingRequest;
import com.laptrinhjavaweb.dto.request.BuildingDeleteRequest;
import com.laptrinhjavaweb.dto.response.BuildingResponse;
import com.laptrinhjavaweb.dto.response.StaffAssignmentResponse;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.impl.UserService;
import com.laptrinhjavaweb.utils.ValidateUtil;
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

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable("id") Long id) {
        buildingService.delete(id);
        return id;
    }
}
