package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.Doctor;
import com.hospital.quanlybenhvien.repository.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// DoctorService.java
// - Chức năng: Xử lý logic nghiệp vụ liên quan đến bác sĩ
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(int id) {
        return doctorRepository.findById(id);
    }

    public List<Doctor> searchDoctors(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return doctorRepository.findAll();
        }
        return doctorRepository.search(keyword);
    }

    public void addDoctor(Doctor doctor) {
        doctorRepository.save(doctor);
    }

    public void updateDoctor(Doctor doctor) {
        doctorRepository.update(doctor);
    }

    public void deleteDoctor(int id) {
        doctorRepository.delete(id);
    }
}

