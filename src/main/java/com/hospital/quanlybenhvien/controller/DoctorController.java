package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Doctor;
import com.hospital.quanlybenhvien.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Xử lý tất cả request liên quan đến bác sĩ (/doctors/...)
@Controller
@RequestMapping("/doctors")
// DoctorController.java
// - Chức năng: Điều phối các yêu cầu HTTP quản lý bác sĩ
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // GET /doctors — danh sách bác sĩ, tìm kiếm theo ?keyword=
    @GetMapping
    public String listDoctors(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<Doctor> doctors;
        if (keyword != null && !keyword.trim().isEmpty()) {
            doctors = doctorService.searchDoctors(keyword); // có keyword → tìm kiếm
            model.addAttribute("keyword", keyword);
        } else {
            doctors = doctorService.getAllDoctors(); // không có keyword → lấy tất cả
        }
        model.addAttribute("doctors", doctors);
        return "doctors/list";
    }

    // GET /doctors/add — mở form thêm bác sĩ mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor()); // object rỗng để Thymeleaf binding
        return "doctors/form";
    }

    // POST /doctors/add — lưu bác sĩ mới vào DB
    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.addDoctor(doctor);
        return "redirect:/doctors";
    }

    // GET /doctors/edit/{id} — mở form sửa, load dữ liệu bác sĩ theo id
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor); // đưa dữ liệu cũ vào form
            return "doctors/form";
        }
        return "redirect:/doctors"; // không tìm thấy → về danh sách
    }

    // POST /doctors/edit — lưu thông tin đã sửa (id được gửi kèm trong form ẩn)
    @PostMapping("/edit")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.updateDoctor(doctor);
        return "redirect:/doctors";
    }

    // GET /doctors/delete/{id} — xóa bác sĩ
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") int id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}