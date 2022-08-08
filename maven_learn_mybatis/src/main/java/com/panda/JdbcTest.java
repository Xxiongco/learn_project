package com.panda;

import org.junit.jupiter.api.Test;

import java.sql.*;

public class JdbcTest {

    @Test
    public void testJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            String url = "jdbc:mysql://localhost:3306/learn";
            String user = "root";
            String password = "root";
            conn = DriverManager.getConnection(url, user, password);
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select * from student");

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" + rs.getString(2) + "\t" + rs.getString(3));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
