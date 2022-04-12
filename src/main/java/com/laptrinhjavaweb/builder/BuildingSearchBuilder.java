package com.laptrinhjavaweb.builder;

import com.laptrinhjavaweb.anotation.LikeField;
import com.laptrinhjavaweb.anotation.OperatorField;
import com.laptrinhjavaweb.anotation.SearchObject;

import java.util.List;

@SearchObject(tableName = "building",aliasValue = "bd",groupBy = true)
public class BuildingSearchBuilder {
    @LikeField
    private String name;

    @OperatorField
    private Integer floorArea;

    @OperatorField
    private String district;

    @LikeField
    private String ward;

    @LikeField
    private String street;

    @OperatorField
    private Integer numberOfBasement;

    @OperatorField
    private String direction;

    @OperatorField
    private String level;

    private Integer rentAreaFrom;

    private Integer rentAreaTo;

    @OperatorField(operator = ">=")
    private Integer rentPriceFrom;

    @OperatorField(operator = "<=")
    private Integer rentPriceTo;

    @LikeField
    private String managerName;

    @LikeField
    private String managerPhone;

    private Integer staffID;

    private List<String> rentTypes;

    public String getName() {
        return name;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public String getDistrict() {
        return district;
    }

    public String getWard() {
        return ward;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumberOfBasement() {
        return numberOfBasement;
    }

    public String getDirection() {
        return direction;
    }

    public String getLevel() {
        return level;
    }

    public Integer getRentAreaFrom() {
        return rentAreaFrom;
    }

    public Integer getRentAreaTo() {
        return rentAreaTo;
    }

    public Integer getRentPriceFrom() {
        return rentPriceFrom;
    }

    public Integer getRentPriceTo() {
        return rentPriceTo;
    }

    public String getManagerName() {
        return managerName;
    }

    public String getManagerPhone() {
        return managerPhone;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public List<String> getRentTypes() {
        return rentTypes;
    }

    private BuildingSearchBuilder(Builder builder) {
        name = builder.name;
        floorArea = builder.floorArea;
        district = builder.district;
        ward = builder.ward;
        street = builder.street;
        numberOfBasement = builder.numberOfBasement;
        direction = builder.direction;
        level = builder.level;
        rentAreaFrom = builder.rentAreaFrom;
        rentAreaTo = builder.rentAreaTo;
        rentPriceFrom = builder.rentPriceFrom;
        rentPriceTo = builder.rentPriceTo;
        managerName = builder.managerName;
        managerPhone = builder.managerPhone;
        staffID = builder.staffID;
        rentTypes = builder.rentTypes;
    }

    public static final class Builder {
        private String name;
        private Integer floorArea;
        private String district;
        private String ward;
        private String street;
        private Integer numberOfBasement;
        private String direction;
        private String level;
        private Integer rentAreaFrom;
        private Integer rentAreaTo;
        private Integer rentPriceFrom;
        private Integer rentPriceTo;
        private String managerName;
        private String managerPhone;
        private Integer staffID;
        private List<String> rentTypes;

        public Builder() {
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder floorArea(Integer val) {
            floorArea = val;
            return this;
        }

        public Builder district(String val) {
            district = val;
            return this;
        }

        public Builder ward(String val) {
            ward = val;
            return this;
        }

        public Builder street(String val) {
            street = val;
            return this;
        }

        public Builder numberOfBasement(Integer val) {
            numberOfBasement = val;
            return this;
        }

        public Builder direction(String val) {
            direction = val;
            return this;
        }

        public Builder level(String val) {
            level = val;
            return this;
        }

        public Builder rentAreaFrom(Integer val) {
            rentAreaFrom = val;
            return this;
        }

        public Builder rentAreaTo(Integer val) {
            rentAreaTo = val;
            return this;
        }

        public Builder rentPriceFrom(Integer val) {
            rentPriceFrom = val;
            return this;
        }

        public Builder rentPriceTo(Integer val) {
            rentPriceTo = val;
            return this;
        }

        public Builder managerName(String val) {
            managerName = val;
            return this;
        }

        public Builder managerPhone(String val) {
            managerPhone = val;
            return this;
        }

        public Builder staffID(Integer val) {
            staffID = val;
            return this;
        }

        public Builder rentTypes(List<String> val) {
            rentTypes = val;
            return this;
        }

        public BuildingSearchBuilder build() {
            return new BuildingSearchBuilder(this);
        }
    }
}
