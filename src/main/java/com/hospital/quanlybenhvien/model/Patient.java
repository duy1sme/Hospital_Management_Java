package com.hospital.quanlybenhvien.model;

// Patient.java
// - Chức năng: Đại diện cho thông tin bệnh nhân
public class Patient {

    private int id;              // Mã số bệnh nhân (Primary Key)
    private String fullName;     // Họ tên đầy đủ
    private String dateOfBirth;  // Ngày sinh (định dạng: yyyy-MM-dd)
    private String gender;       // Giới tính (Nam hoặc Nữ)
    private String phone;        // Số điện thoại liên hệ
    private String address;      // Địa chỉ nhà ở
    private String diagnosis;    // Chẩn đoán bệnh ban đầu
    private String createdAt;    // Ngày tạo hồ sơ (tự động ghi khi thêm)

    // Constructor không tham số
    public Patient() {}

    // ===== GETTERS VÀ SETTERS =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDiagnosis() { return diagnosis; } // Lấy chẩn đoán bệnh ban đầu
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; } // Cập nhật chẩn đoán

    public String getCreatedAt() { return createdAt; } // Lấy ngày tạo bệnh án
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; } // Cập nhật ngày tạo
}