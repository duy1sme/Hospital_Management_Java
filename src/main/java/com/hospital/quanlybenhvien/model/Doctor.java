package com.hospital.quanlybenhvien.model;

// Doctor.java
// - Chức năng: Đại diện cho thông tin bác sĩ
public class Doctor {

    private int id;              // Mã số bác sĩ (Primary Key)
    private String fullName;     // Họ tên đầy đủ
    private String specialty;    // Chuyên khoa (ví dụ: Ngoại khoa, Tim mạch, ...)
    private String phone;        // Số điện thoại liên hệ
    private String email;        // Địa chỉ email
    private boolean active;      // Trạng thái: true=đang làm việc, false=nghỉ làm

    // Constructor không tham số
    public Doctor() {}

    // ===== GETTERS VÀ SETTERS =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getSpecialty() { return specialty; }
    public void setSpecialty(String specialty) { this.specialty = specialty; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public boolean isActive() { return active; } // Kiểm tra bác sĩ có đang làm việc không
    public void setActive(boolean active) { this.active = active; } // Cập nhật trạng thái làm việc
}