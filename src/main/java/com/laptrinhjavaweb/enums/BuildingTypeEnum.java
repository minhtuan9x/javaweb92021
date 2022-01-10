package com.laptrinhjavaweb.enums;

public enum BuildingTypeEnum {
    TANG_TRET("Tầng trệt"),
    NGUYEN_CAN("Nguyên căn"),
    NOI_THAT("Nội thất");

    private final String buildingTypeValue;

    BuildingTypeEnum(String buildingTypeValue) {
        this.buildingTypeValue = buildingTypeValue;
    }

    public String getBuildingTypeValue() {
        return buildingTypeValue;
    }
}
