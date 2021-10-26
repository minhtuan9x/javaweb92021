package com.dominhtuan.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDBUtil {
    private static String url = "jdbc:mysql://localhost:3306/estatebasic";
    private static String user = "root";
    private static String password = "admin";

    public static Connection connectDB() throws SQLException {
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
