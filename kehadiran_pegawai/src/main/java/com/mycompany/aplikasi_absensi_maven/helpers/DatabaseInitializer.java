package com.mycompany.aplikasi_absensi_maven.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initializeDatabase() {
        String url = "jdbc:sqlite:attendance.db"; // Lokasi file database SQLite

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                // Statement untuk eksekusi SQL
                Statement stmt = conn.createStatement();

                // Membuat tabel users
                String createUserTable = "CREATE TABLE IF NOT EXISTS users (\n"
                        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + " username TEXT NOT NULL UNIQUE,\n"
                        + " password TEXT NOT NULL\n"
                        + ");";
                stmt.execute(createUserTable);

                // Menambahkan akun default untuk testing
                String insertDefaultUser = "INSERT INTO users (username, password) VALUES ('admin', 'admin')";
                stmt.execute(insertDefaultUser);

                // Membuat tabel employees
                String createEmployeeTable = "CREATE TABLE IF NOT EXISTS employees (\n"
                        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + " name TEXT NOT NULL,\n"
                        + " position TEXT NOT NULL\n"
                        + ");";
                stmt.execute(createEmployeeTable);

                // Insert employees if not already present
                String insertEmployee1 = "INSERT OR IGNORE INTO employees (name, position) VALUES ('Employee1', 'Position1')";
                stmt.execute(insertEmployee1);

                String insertEmployee2 = "INSERT OR IGNORE INTO employees (name, position) VALUES ('Employee2', 'Position2')";
                stmt.execute(insertEmployee2);

                // Membuat tabel attendance
                String createAttendanceTable = "CREATE TABLE IF NOT EXISTS attendance (\n"
                        + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                        + " employee_id INTEGER,\n"
                        + " status TEXT NOT NULL DEFAULT 'Present',\n"
                        + " keterangan TEXT NOT NULL DEFAULT 'Present',\n"
                        + " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP,\n"
                        + " FOREIGN KEY (employee_id) REFERENCES employees(id)\n"
                        + ");";
                stmt.execute(createAttendanceTable);

                System.out.println("Database telah diinisialisasi.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        initializeDatabase();
    }
}
