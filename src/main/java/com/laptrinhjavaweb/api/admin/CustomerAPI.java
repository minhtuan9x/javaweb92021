package com.laptrinhjavaweb.api.admin;

import com.laptrinhjavaweb.dto.CustomerDTO;
import com.laptrinhjavaweb.dto.response.StaffAssignmentResponse;
import com.laptrinhjavaweb.service.CustomerService;
import com.laptrinhjavaweb.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customer")
public class CustomerAPI {
    @Autowired
    private IUserService userService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/{id}/staff")
    public List<StaffAssignmentResponse> getAllStaffByCustomer(@PathVariable("id") Long id) {
        return userService.getAllStaffAssignmentCustomer(id);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> findOne(@PathVariable Long id){
        return ResponseEntity.ok().body(customerService.findById(id));
    }
    @PostMapping("/{id}/assignment")
    public Long assignmentBuilding(@RequestBody(required = false) List<Long> staffIds
            , @PathVariable("id") Long customerId) throws NotFoundException {
        customerService.assignmentCustomer(staffIds, customerId);
        return customerId;
    }
    @PostMapping
    public ResponseEntity<CustomerDTO> save(@RequestBody CustomerDTO customerDTO) throws NotFoundException {
        return ResponseEntity.ok().body(customerService.save(customerDTO));
    }
    @DeleteMapping
    public ResponseEntity<Void> delete(@RequestBody List<Long> ids) throws NotFoundException {
        customerService.delete(ids);
        return ResponseEntity.noContent().build();
    }
}
