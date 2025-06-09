package com.clinic.appointment.controller;

import com.clinic.appointment.model.Appointment;
import com.clinic.appointment.repository.AppointmentRepository;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.PatientRepository;
import com.clinic.appointment.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    @GetMapping("/appointments/book")
    public String showBookingForm(Model model) {
        model.addAttribute("appointment", new Appointment());
        model.addAttribute("doctors", doctorRepository.findAll());
        model.addAttribute("patients", patientRepository.findAll());
        return "booking";
    }
    @GetMapping("/appointments")
    public String listAppointments(Model model) {
        model.addAttribute("appointments", appointmentRepository.findAll());
        return "appointments";
    }

    @GetMapping("/appointments/delete/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentRepository.deleteById(id);
        return "redirect:/appointments";
    }

    @PostMapping("/appointments/book")
    public String bookAppointment(@ModelAttribute Appointment appointment, Model model) {
        try {
            Appointment booked = appointmentService.bookAppointment(appointment);
            model.addAttribute("appointment", booked);
            return "confirmation";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("doctors", doctorRepository.findAll());
            model.addAttribute("patients", patientRepository.findAll());
            return "booking";
        }
    }
}