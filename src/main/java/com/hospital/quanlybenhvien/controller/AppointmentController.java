package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Appointment;
import com.hospital.quanlybenhvien.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Xử lý tất cả request liên quan đến lịch hẹn (/appointments/...)
@Controller
@RequestMapping("/appointments")
// AppointmentController.java
// - Chức năng: Điều phối các yêu cầu HTTP quản lý lịch hẹn khám
public class AppointmentController {

    private final AppointmentService appointmentService;

    // Spring tự inject AppointmentService qua constructor
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // GET /appointments — hiển thị danh sách, hỗ trợ tìm kiếm qua ?keyword=...
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        // searchAppointments tự xử lý: keyword rỗng → lấy tất cả
        model.addAttribute("appointments", appointmentService.searchAppointments(keyword));
        model.addAttribute("keyword", keyword); // giữ lại keyword để hiển thị lại trên form
        return "appointments/list";
    }

    // GET /appointments/add — mở form đặt lịch mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("appointment", new Appointment()); // object rỗng để form binding
        model.addAttribute("patients", appointmentService.getAllPatients()); // dropdown bệnh nhân
        model.addAttribute("doctors",  appointmentService.getAllDoctors());  // dropdown bác sĩ
        return "appointments/form";
    }

    // POST /appointments/add — nhận dữ liệu form, lưu vào DB, redirect về danh sách
    @PostMapping("/add")
    public String addAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.addAppointment(appointment);
        return "redirect:/appointments"; // tránh submit lại form khi F5
    }

    // POST /appointments/status/{id} — đổi trạng thái lịch hẹn (dropdown trực tiếp trên bảng)
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable int id,
                               @RequestParam String status) {
        appointmentService.updateStatus(id, status);
        return "redirect:/appointments";
    }

    // GET /appointments/delete/{id} — xóa lịch hẹn
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}