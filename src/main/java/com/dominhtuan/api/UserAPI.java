package com.dominhtuan.api;


import com.dominhtuan.dto.UserDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserAPI {

    @GetMapping("/api/staffs")
    public List<UserDTO> getStaffs(){
        return null;
    }
}
