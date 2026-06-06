package com.hospital.quanlybenhvien.service;

import com.hospital.quanlybenhvien.model.Appointment;
import com.hospital.quanlybenhvien.model.Doctor;
import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.repository.AppointmentRepository;
import com.hospital.quanlybenhvien.repository.DoctorRepository;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    private final PatientRepository patientRepository;

    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> searchAppointments(String keyword) {

        if (keyword == null || keyword.trim().isEmpty()) {
            return appointmentRepository.findAll();
        }

        return appointmentRepository.search(keyword);
    }

    public Appointment getAppointmentById(int id) {
        return appointmentRepository.findById(id);
    }

    public void addAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    public void updateStatus(int id, String status) {
        appointmentRepository.updateStatus(id, status);
    }

    public void deleteAppointment(int id) {
        appointmentRepository.delete(id);
    }

    public int countToday() {
        return appointmentRepository.countToday();
    }

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }
}
