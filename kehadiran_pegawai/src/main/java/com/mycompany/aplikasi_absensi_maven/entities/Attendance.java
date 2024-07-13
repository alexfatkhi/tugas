/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.aplikasi_absensi_maven.entities;


public class Attendance {
    private int id , employee_id;
    private String status , keterangan, name, timestamp;

    public Attendance(int id, int employee_id, String status, String keterangan, String name , String timestamp) {
        this.id = id;
        this.employee_id = employee_id;
        this.status = status;
        this.keterangan = keterangan;
        this.name = name;
        this.timestamp
                 = timestamp;
    }

    @Override
    public String toString() {
        return "Attendance{" + "id=" + id + ", employee_id=" + employee_id + ", status=" + status + ", keterangan=" + keterangan + ", name=" + name + ", timestamp=" + timestamp + '}';
    }

    public String getName() {
        return name;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(int employee_id) {
        this.employee_id = employee_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
    
    
}
