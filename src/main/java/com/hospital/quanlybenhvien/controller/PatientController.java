package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// Xử lý tất cả request liên quan đến bệnh nhân (/patients/...)
@Controller
@RequestMapping("/patients")
// PatientController.java
// - Chức năng: Điều phối các yêu cầu HTTP quản lý bệnh nhân
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // GET /patients — danh sách bệnh nhân, hỗ trợ tìm kiếm theo ?keyword=
    @GetMapping
    public String listPatients(
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {
        // searchPatients tự xử lý: keyword null/rỗng → findAll(), ngược lại → search()
        model.addAttribute("patients", patientService.searchPatients(keyword));
        model.addAttribute("keyword", keyword); // giữ lại để hiển thị lại trong ô tìm kiếm
        return "patients/list";
    }

    // GET /patients/add — mở form thêm bệnh nhân mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Thêm bệnh nhân"); // hiển thị tiêu đề form
        return "patients/form";
    }

    // POST /patients/add — lưu bệnh nhân mới vào DB
    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);
        return "redirect:/patients";
    }

    // GET /patients/edit/{id} — mở form sửa, dùng Long vì URL path dùng kiểu Long
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);
        if (patient == null) {
            return "redirect:/patients"; // không tìm thấy → về danh sách
        }
        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Sửa bệnh nhân");
        return "patients/form";
    }

    // POST /patients/edit/{id} — lưu thông tin đã sửa
    // Math.toIntExact(): chuyển Long → int (vì DB dùng INT)
    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        patient.setId(Math.toIntExact(id)); // gán id để Repository biết UPDATE dòng nào
        patientService.updatePatient(patient);
        return "redirect:/patients";
    }

    // GET /patients/delete/{id} — xóa bệnh nhân
    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return "redirect:/patients";
    }
}