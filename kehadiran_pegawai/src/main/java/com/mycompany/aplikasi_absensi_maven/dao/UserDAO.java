package com.mycompany.aplikasi_absensi_maven.dao;

import com.mycompany.aplikasi_absensi_maven.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean authenticate(String username, String password) {
        String query = "SELECT * FROM users WHERE username=? AND password=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Authentication error: " + e.getMessage());
            return false;
        }
    }

    public int getTotalUsers() {
        try {
            String query = "SELECT COUNT(*) AS total FROM users";
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

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM users";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet res =  pstmt.executeQuery();
            while(res.next()){
                users.add(new User(res.getString("username") , res.getString("password") , res.getInt("id")));
            }
            return users;
        } catch (SQLException e) {
            System.out.println("Error fetching all users: " + e.getMessage());
            return users;
        }
    }

    public boolean insertUser(String username, String password) {
        try {
            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error inserting user: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(int userId, String username, String password) {
        String query = "UPDATE users SET username=?, password=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setInt(3, userId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Update error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(int id) {
        String query = "DELETE FROM users WHERE id=?";
        try(PreparedStatement pstmt = conn.prepareStatement(query);
                ) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            return false;
        }
    }

}
