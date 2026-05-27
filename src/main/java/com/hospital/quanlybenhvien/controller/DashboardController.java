package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.repository.AppointmentRepository;
import com.hospital.quanlybenhvien.repository.DoctorRepository;
import com.hospital.quanlybenhvien.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

// Controller cho trang chủ (Dashboard) và trang Login
@Controller
// DashboardController.java
// - Chức năng: Hiển thị trang chủ thống kê tổng quan (Dashboard)
public class DashboardController {

    // Inject trực tiếp Repository thay vì qua Service
    // vì chỉ cần đọc số liệu đơn giản, không cần logic nghiệp vụ
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

    // GET / — trang chủ, truyền 4 con số thống kê lên Dashboard
    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients",     patientRepository.count());       // tổng bệnh nhân
        model.addAttribute("totalDoctors",      doctorRepository.count());        // tổng bác sĩ
        model.addAttribute("activeDoctors",     doctorRepository.countActive());  // bác sĩ đang làm
        model.addAttribute("totalAppointments", appointmentRepository.countToday()); // lịch hẹn hôm nay
        return "index"; // → templates/index.html
    }

    // GET /login — Spring Security sẽ tự xử lý POST /login
    // Controller chỉ cần trả về trang HTML
    @GetMapping("/login")
    public String login() {
        return "login"; // → templates/login.html
    }
}