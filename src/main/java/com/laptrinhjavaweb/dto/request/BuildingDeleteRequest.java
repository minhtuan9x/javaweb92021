package com.laptrinhjavaweb.dto.request;

import java.util.List;

public class BuildingDeleteRequest {
    List<Long> buildingIDs;

    public List<Long> getBuildingIDs() {
        return buildingIDs;
    }

    public void setBuildingIDs(List<Long> buildingIDs) {
        this.buildingIDs = buildingIDs;
    }
}
