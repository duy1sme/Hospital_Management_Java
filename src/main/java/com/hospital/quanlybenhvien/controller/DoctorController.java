package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Doctor;
import com.hospital.quanlybenhvien.service.DoctorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/doctors")

public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public String listDoctors(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        List<Doctor> doctors;
        if (keyword != null && !keyword.trim().isEmpty()) {
            doctors = doctorService.searchDoctors(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            doctors = doctorService.getAllDoctors();
        }
        model.addAttribute("doctors", doctors);
        return "doctors/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctors/form";
    }

    @PostMapping("/add")
    public String addDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.addDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") int id, Model model) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor != null) {
            model.addAttribute("doctor", doctor);
            return "doctors/form";
        }
        return "redirect:/doctors";
    }

    @PostMapping("/edit")
    public String updateDoctor(@ModelAttribute("doctor") Doctor doctor) {
        doctorService.updateDoctor(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable("id") int id) {
        doctorService.deleteDoctor(id);
        return "redirect:/doctors";
    }
}
