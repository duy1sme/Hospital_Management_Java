package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Patient> searchPatients(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return patientRepository.findAll();
        }

        return patientRepository.search(keyword.trim());
    }

    public Patient getPatientById(Long id) {
        return patientRepository.findById(Math.toIntExact(id));
    }

    public void savePatient(Patient patient) {
        patientRepository.save(patient);
    }

    public void updatePatient(Patient patient) {
        patientRepository.update(patient);
    }

    public void deletePatient(Long id) {
        patientRepository.delete(Math.toIntExact(id));
    }
}
