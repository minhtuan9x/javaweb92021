package com.dominhtuan.jdbc.impl;

import com.dominhtuan.entity.DistrictEntity;
import com.dominhtuan.jdbc.DistrictJDBC;
import com.dominhtuan.util.ConnectDBUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class DistrictJDBCImpl implements DistrictJDBC {
	private Connection conn = null;
	private Statement stmt;
	private ResultSet rs;
	private ConnectDBUtil connectDBUtil = new ConnectDBUtil();

	@Override
	public DistrictEntity findDistrictByDistrictID(long districtID) throws SQLException {
		DistrictEntity districtEntity = new DistrictEntity();
		try {
			conn = connectDBUtil.connectDB();
			if (conn != null) {
				conn.setAutoCommit(false);
				stmt = conn.createStatement();
				String sql = "select * from district where id = " + districtID;
				rs = stmt.executeQuery(sql);
				while (rs.next()) {
					districtEntity.setId(rs.getLong("id"));
					districtEntity.setCode(rs.getString("code"));
					districtEntity.setName(rs.getString("name"));
				}
			}
			conn.commit();
		} catch (Exception e) {
			conn.rollback();
			System.out.println("Loi districtjdbc");
			e.printStackTrace();
		} finally {
			conn.close();
			stmt.close();
			rs.close();
		}
		return districtEntity;
	}
}
