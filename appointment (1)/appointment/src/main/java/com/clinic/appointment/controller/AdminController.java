package com.clinic.appointment.controller;

import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AdminController {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // ------------------ DOCTOR CRUD ------------------

    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        List<Doctor> doctors = doctorRepository.findAll();
        model.addAttribute("doctors", doctors);
        return "doctor_list";
    }

    @GetMapping("/doctors/new")
    public String showDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "doctor_form";
    }

    @PostMapping("/doctors")
    public String saveDoctor(@ModelAttribute Doctor doctor) {
        doctorRepository.save(doctor);
        return "redirect:/doctors";
    }

    @GetMapping("/doctors/edit/{id}")
    public String editDoctor(@PathVariable Long id, Model model) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow();
        model.addAttribute("doctor", doctor);
        return "doctor_form";
    }

    @GetMapping("/doctors/delete/{id}")
    public String deleteDoctor(@PathVariable Long id) {
        doctorRepository.deleteById(id);
        return "redirect:/doctors";
    }

    // ------------------ PATIENT CRUD ------------------

    @GetMapping("/patients")
    public String listPatients(Model model) {
        List<Patient> patients = patientRepository.findAll();
        model.addAttribute("patients", patients);
        return "patient_list";
    }

    @GetMapping("/patients/new")
    public String showPatientForm(Model model) {
        model.addAttribute("patient", new Patient());
        return "patient_form";
    }

    @PostMapping("/patients")
    public String savePatient(@ModelAttribute Patient patient) {
        patientRepository.save(patient);
        return "redirect:/patients";
    }

    @GetMapping("/patients/edit/{id}")
    public String editPatient(@PathVariable Long id, Model model) {
        Patient patient = patientRepository.findById(id).orElseThrow();
        model.addAttribute("patient", patient);
        return "patient_form";
    }

    @GetMapping("/patients/delete/{id}")
    public String deletePatient(@PathVariable Long id) {
        patientRepository.deleteById(id);
        return "redirect:/patients";
    }
}