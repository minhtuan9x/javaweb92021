package com.laptrinhjavaweb.dto.request;

import java.util.ArrayList;
import java.util.List;

public class BuildingDelRequest {
    private List<Long> buildingIds = new ArrayList<>();

    public List<Long> getBuildingIds() {
        return buildingIds;
    }

    public void setBuildingIds(List<Long> buildingIds) {
        this.buildingIds = buildingIds;
    }
}
