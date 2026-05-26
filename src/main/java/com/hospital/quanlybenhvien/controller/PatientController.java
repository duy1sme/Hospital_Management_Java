package com.hospital.quanlybenhvien.controller;

import com.hospital.quanlybenhvien.model.Patient;
import com.hospital.quanlybenhvien.service.PatientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String listPatients(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        model.addAttribute("patients", patientService.searchPatients(keyword));
        model.addAttribute("keyword", keyword);

        return "patients/list";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("patient", new Patient());
        model.addAttribute("pageTitle", "Thêm bệnh nhân");

        return "patients/form";
    }

    @PostMapping("/add")
    public String addPatient(@ModelAttribute Patient patient) {
        patientService.savePatient(patient);

        return "redirect:/patients";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Patient patient = patientService.getPatientById(id);

        if (patient == null) {
            return "redirect:/patients";
        }

        model.addAttribute("patient", patient);
        model.addAttribute("pageTitle", "Sửa bệnh nhân");

        return "patients/form";
    }

    @PostMapping("/edit/{id}")
    public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
        patient.setId(Math.toIntExact(id));
        patientService.updatePatient(patient);

        return "redirect:/patients";
    }

    @GetMapping("/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);

        return "redirect:/patients";
    }
}
