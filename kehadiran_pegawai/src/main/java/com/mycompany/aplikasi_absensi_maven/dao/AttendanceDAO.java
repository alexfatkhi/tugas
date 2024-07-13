package com.mycompany.aplikasi_absensi_maven.dao;

import com.mycompany.aplikasi_absensi_maven.entities.Attendance;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AttendanceDAO {

    private Connection conn;

    public AttendanceDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean setAttendance(int employeeId, String status, String keterangan) {
        try {
            String updateQuery = "INSERT INTO attendance (employee_id, status , keterangan , timestamp) VALUES (?, ? , ?, current_timestamp)";
            PreparedStatement pstmt = conn.prepareStatement(updateQuery);
            pstmt.setInt(1, employeeId);
            pstmt.setString(2, status);
            pstmt.setString(3, keterangan);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error marking employee absent: " + e.getMessage());
            return false;
        }
    }

    public List<Attendance> getAllAttendancesWithEmployeeName() {
        List<Attendance> attendances = new ArrayList<>();
        String query = "SELECT a.id, a.employee_id, e.name, a.status, a.keterangan, a.timestamp " +
                       "FROM attendance a " +
                       "JOIN employees e ON a.employee_id = e.id";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int employeeId = rs.getInt("employee_id");
                String employeeName = rs.getString("name");
                String status = rs.getString("status");
                String keterangan = rs.getString("keterangan");
                String timestamp = rs.getString("timestamp");
                Attendance attendance = new Attendance(id, employeeId,  status, keterangan, employeeName,timestamp);
                attendances.add(attendance);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching attendances: " + e.getMessage());
        }
        return attendances;
    }

    public boolean isEmployeAbsenToday(int employeeId) {
        String sql = "select * from attendance where employee_id=? and date(timestamp)=?";
        LocalDate today = LocalDate.now();
        try (PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, employeeId);
            pst.setString(2, today.toString());
            return pst.executeQuery().next();
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean deleteAttendance(int id){
       String query = "delete from attendance where id=?";
        try(PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, id);
            int row = pst.executeUpdate();
            return row > 0;
        } catch (Exception e) {
        return false;
        }
    }

}
