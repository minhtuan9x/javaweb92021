package com.laptrinhjavaweb.dto.request;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBuildingRequest {
    List<Long> staffIDs = new ArrayList<>();

    public List<Long> getStaffIDs() {
        return staffIDs;
    }

    public void setStaffIDs(List<Long> staffIDs) {
        this.staffIDs = staffIDs;
    }
}
