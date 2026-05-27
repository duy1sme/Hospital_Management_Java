package com.hospital.quanlybenhvien.model;

// Appointment.java
// - Chức năng: Đại diện cho thông tin lịch hẹn khám
public class Appointment {

    private int id;                   // Mã số lịch hẹn (Primary Key)
    private Integer patientId;        // Mã bệnh nhân (dùng Integer có thể nullable)
    private String patientName;       // Tên bệnh nhân (từ bảng patients - result của JOIN)
    private Integer doctorId;         // Mã bác sĩ (dùng Integer có thể nullable)
    private String doctorName;        // Tên bác sĩ (từ bảng doctors - result của JOIN)
    private String appointmentDate;   // Ngày khám (định dạng: yyyy-MM-dd)
    private String appointmentTime;   // Giờ khám (định dạng: HH:mm)
    private String status;            // Trạng thái: Chờ xác nhận/Đã xác nhận/Hoàn thành/Đã hủy
    private String note;              // Ghi chú/triệu chứng bệnh nhân
    private String createdAt;         // Ngày tạo lịch hẹn (tự động ghi)

    // Constructor không tham số
    public Appointment() {}

    // ===== GETTERS VÀ SETTERS =====
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Lấy/cập nhật ID bệnh nhân
    public Integer getPatientId() { return patientId; }
    public void setPatientId(int patientId) { this.patientId = patientId; }

    // Lấy/cập nhật tên bệnh nhân (từ kết quả JOIN)
    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    // Lấy/cập nhật ID bác sĩ
    public Integer getDoctorId() { return doctorId; }
    public void setDoctorId(int doctorId) { this.doctorId = doctorId; }

    // Lấy/cập nhật tên bác sĩ (từ kết quả JOIN)
    public String getDoctorName() { return doctorName; }
    public void setDoctorName(String doctorName) { this.doctorName = doctorName; }

    // Lấy/cập nhật ngày khám
    public String getAppointmentDate() { return appointmentDate; }
    public void setAppointmentDate(String appointmentDate) { this.appointmentDate = appointmentDate; }

    // Lấy/cập nhật giờ khám
    public String getAppointmentTime() { return appointmentTime; }
    public void setAppointmentTime(String appointmentTime) { this.appointmentTime = appointmentTime; }

    // Lấy/cập nhật trạng thái lịch hẹn
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // Lấy/cập nhật ghi chú triệu chứng
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    // Lấy/cập nhật ngày tạo
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}