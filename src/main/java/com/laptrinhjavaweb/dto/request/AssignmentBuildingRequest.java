package com.laptrinhjavaweb.dto.request;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBuildingRequest {
    List<Integer> staffIDs = new ArrayList<>();

    public List<Integer> getStaffIDs() {
        return staffIDs;
    }

    public void setStaffIDs(List<Integer> staffIDs) {
        this.staffIDs = staffIDs;
    }
}
