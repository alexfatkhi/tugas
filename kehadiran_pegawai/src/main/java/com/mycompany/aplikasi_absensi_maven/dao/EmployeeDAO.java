package com.mycompany.aplikasi_absensi_maven.dao;

import com.mycompany.aplikasi_absensi_maven.dao.*;
import com.mycompany.aplikasi_absensi_maven.entities.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private Connection conn;

    public EmployeeDAO(Connection conn) {
        this.conn = conn;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        try {
            String query = "SELECT * FROM employees";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString(2));
                String name = rs.getString("name");
                employees.add(new Employee(name, rs.getString("position"), rs.getInt("id")));
            }       
        } catch (SQLException e) {
            System.out.println("Error retrieving employee names: " + e.getMessage());
        }
        return employees;
    }

    public int getTotalEmployee() {
        try {
            String query = "SELECT COUNT(*) AS total FROM employees";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching total users: " + e.getMessage());
            return 0;
        }
    }

    public boolean deleteEmployee(int id) {
        String query = "delete from employees where id = ?";
        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
   

    public boolean insert(String name, String position) {
        String sql = "insert into employees (name ,position) values(?, ?)";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, position);
            int rowInserted = pst.executeUpdate();
            return rowInserted > 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean update(int id, String name, String position) {
        String sql = "update employees set name=? , position=? where id=?";
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setString(1, name);
            pst.setString(2, position);
            pst.setInt(3, id);
            int rowUpdated = pst.executeUpdate();
            return rowUpdated > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
