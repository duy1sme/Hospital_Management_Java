package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.Appointment;
import com.hospital.quanlybenhvien.model.Doctor;
import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.repository.AppointmentRepository;
import com.hospital.quanlybenhvien.repository.DoctorRepository;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service // Đánh dấu đây là lớp Service của Spring
// AppointmentService.java
// - Chức năng: Xử lý logic nghiệp vụ liên quan đến lịch hẹn khám
public class AppointmentService {

    // Repository thao tác với bảng Appointment
    private final AppointmentRepository appointmentRepository;

    // Repository thao tác với bảng Patient
    private final PatientRepository patientRepository;

    // Repository thao tác với bảng Doctor
    private final DoctorRepository doctorRepository;

    // Constructor Injection để Spring tự động inject dependency
    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // Lấy toàn bộ danh sách lịch hẹn
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // Tìm kiếm lịch hẹn theo từ khóa
    public List<Appointment> searchAppointments(String keyword) {

        // Nếu keyword rỗng hoặc null thì trả về toàn bộ dữ liệu
        if (keyword == null || keyword.trim().isEmpty()) {
            return appointmentRepository.findAll();
        }

        // Ngược lại thực hiện tìm kiếm
        return appointmentRepository.search(keyword);
    }

    // Lấy thông tin lịch hẹn theo ID
    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id);
    }

    // Thêm lịch hẹn mới
    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    // Cập nhật trạng thái lịch hẹn
    public void updateStatus(int id, String status) {
        appointmentRepository.updateStatus(id, status);
    }

    // Xóa lịch hẹn theo ID
    public void deleteAppointment(int id) {
        appointmentRepository.delete(id);
    }

    // Đếm số lịch hẹn trong ngày hôm nay
    public int countToday() {
        return appointmentRepository.countToday();
    }

    // Lấy toàn bộ danh sách bệnh nhân để hiển thị trong form
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Lấy toàn bộ danh sách bác sĩ để hiển thị trong form
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}