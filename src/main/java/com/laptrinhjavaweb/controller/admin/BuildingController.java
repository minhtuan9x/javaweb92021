package com.laptrinhjavaweb.controller.admin;

import com.laptrinhjavaweb.api.admin.BuildingAPI;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.BuildingDTO;
import com.laptrinhjavaweb.dto.request.BuildingSearchRequest;
import com.laptrinhjavaweb.service.BuildingService;
import com.laptrinhjavaweb.service.BuildingTypeService;
import com.laptrinhjavaweb.service.DistrictService;
import com.laptrinhjavaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class BuildingController {

    @Autowired
    private DistrictService districtService;
    @Autowired
    private BuildingTypeService buildingTypeService;
    @Autowired
    private UserService userService;
    @Autowired
    private BuildingService buildingService;
    @Autowired
    private BuildingConverter buildingConverter;

    @GetMapping("/building-list")
    public ModelAndView buildingList(@ModelAttribute("modelSearch") BuildingSearchRequest buildingSearchRequest){
        ModelAndView modelAndView = new ModelAndView("admin/building/list");
        modelAndView.addObject("modelDistrict",districtService.getAll());
        modelAndView.addObject("modelStaff",userService.getAllStaff());
        modelAndView.addObject("modelBuildingType",buildingTypeService.getAll());
        modelAndView.addObject("modelBuildings",buildingService.findAll(buildingSearchRequest));
        return modelAndView;
    }
    @GetMapping("/building-edit")
    public  ModelAndView buildingEdit(@RequestParam(name = "buildingid",required = false) Long id){
        ModelAndView modelAndView = new ModelAndView("admin/building/edit");
        if(id!=null){
            modelAndView.addObject("modelDistrict",districtService.getAllByBuilding(buildingService.findById(id)));
            modelAndView.addObject("modelBuildingType",buildingTypeService.getAllByBuilding(buildingService.findById(id)));
            modelAndView.addObject("modelBuilding",buildingService.findById(id));
        }else{
            modelAndView.addObject("modelDistrict",districtService.getAll());
            modelAndView.addObject("modelBuildingType",buildingTypeService.getAll());
            modelAndView.addObject("modelBuilding",new BuildingDTO());
        }
        return modelAndView;
    }

}
