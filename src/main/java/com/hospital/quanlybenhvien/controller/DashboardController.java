package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.repository.AppointmentRepository;
import com.hospital.quanlybenhvien.repository.DoctorRepository;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public DashboardController(PatientRepository patientRepository,
                               DoctorRepository doctorRepository,
                               AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients",     patientRepository.count());
        model.addAttribute("totalDoctors",      doctorRepository.count());
        model.addAttribute("activeDoctors",     doctorRepository.countActive());
        model.addAttribute("totalAppointments", appointmentRepository.countToday()); // ← thêm
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}