package com.dominhtuan.jdbc;

import com.dominhtuan.entity.DistrictEntity;

import java.sql.SQLException;

public interface DistrictJDBC {
    DistrictEntity findByDistrictID(long districtID) throws SQLException;
}
