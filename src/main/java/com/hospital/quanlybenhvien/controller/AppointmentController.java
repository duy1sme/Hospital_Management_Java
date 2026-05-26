package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Appointment;
import com.hospital.quanlybenhvien.service.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    // Danh sách lịch hẹn
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        model.addAttribute("appointments", appointmentService.searchAppointments(keyword));
        model.addAttribute("keyword", keyword);
        return "appointments/list";
    }

    // Hiện form đặt lịch mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("patients", appointmentService.getAllPatients());
        model.addAttribute("doctors",  appointmentService.getAllDoctors());
        return "appointments/form";
    }

    // Lưu lịch hẹn mới
    @PostMapping("/add")
    public String addAppointment(@ModelAttribute Appointment appointment) {
        appointmentService.addAppointment(appointment);
        return "redirect:/appointments";
    }

    // Cập nhật trạng thái (xác nhận / hoàn thành / hủy)
    @PostMapping("/status/{id}")
    public String updateStatus(@PathVariable int id,
                               @RequestParam String status) {
        appointmentService.updateStatus(id, status);
        return "redirect:/appointments";
    }

    // Xóa lịch hẹn
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}