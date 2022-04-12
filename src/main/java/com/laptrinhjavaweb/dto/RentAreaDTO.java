package com.laptrinhjavaweb.dto;

public class RentAreaDTO extends AbstractDTO{
    private Long buildingID;
    private Integer value;

    public Long getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(Long buildingID) {
        this.buildingID = buildingID;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
