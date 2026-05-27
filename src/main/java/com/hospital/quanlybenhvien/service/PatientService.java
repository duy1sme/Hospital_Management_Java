package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

// PatientService - Lớp phục vụ cho logic quản lý bệnh nhân
// Giao tiếp giữa Controller và Repository
@Service
// PatientService.java
// - Chức năng: Xử lý logic nghiệp vụ liên quan đến bệnh nhân
public class PatientService {

    private final PatientRepository patientRepository;

    // Constructor injection
    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // Lấy danh sách tất cả bệnh nhân
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    // Tìm kiếm bệnh nhân theo keyword (tên hoặc SĐT)
// Nếu keyword rỗng → trả về tất cả
    public List<Patient> searchPatients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return patientRepository.findAll();
        }

        return patientRepository.search(keyword.trim());
    }

    // Lấy bệnh nhân theo ID
// @param id: ID bệnh nhân (kiểu Long từ form, chuyển thành int)
    public Patient getPatientById(Long id) {
        return patientRepository.findById(Math.toIntExact(id)); // Chuyển Long → int
    }

    // Lưu (thêm) bệnh nhân mới
    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    // Cập nhật thông tin bệnh nhân
    public void updatePatient(Patient patient) {
        patientRepository.update(patient);
    }

    // Xóa bệnh nhân
// @param id: ID bệnh nhân (kiểu Long từ form, chuyển thành int)
    public void deletePatient(Long id) {
        patientRepository.delete(Math.toIntExact(id)); // Chuyển Long → int
    }
}
