package com.dominhtuan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDBUtil {
    private String url = "jdbc:mysql://localhost:3306/estatebasic";
    private String user = "root";
    private String password = "admin";

    public Connection connectDB() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
