package com.laptrinhjavaweb.controller.admin;

import com.laptrinhjavaweb.dto.request.CustomerSearchRequest;
import com.laptrinhjavaweb.service.CustomerService;
import com.laptrinhjavaweb.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private UserService userService;

    @GetMapping("/customer-list")
    public ModelAndView list(@ModelAttribute(name = "modelSearch") CustomerSearchRequest customerSearchRequest){
        ModelAndView modelAndView = new ModelAndView("admin/customer/list");
        modelAndView.addObject("customers",customerService.findAll(customerSearchRequest));
        modelAndView.addObject("modelStaff",userService.getAllStaff());
        return modelAndView;
    }
    @GetMapping("/customer-edit")
    public ModelAndView edit(@RequestParam(required = false) Long id){
        ModelAndView modelAndView = new ModelAndView("admin/customer/edit");
        modelAndView.addObject("customer",customerService.findById(id));
        return modelAndView;
    }
}
