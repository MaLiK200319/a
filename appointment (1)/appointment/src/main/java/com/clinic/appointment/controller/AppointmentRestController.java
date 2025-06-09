package com.clinic.appointment.controller;

import com.clinic.appointment.model.Appointment;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.repository.AppointmentRepository;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@CrossOrigin(origins = "*") // optional, helpful for Postman or frontend
public class AppointmentRestController {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    // 🔹 GET all appointments
    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    // 🔹 GET appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 POST create new appointment
    @PostMapping
    public ResponseEntity<?> createAppointment(@RequestBody Appointment appointment) {
        try {
            // Check Doctor & Patient exist
            Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
            Patient patient = patientRepository.findById(appointment.getPatient().getId())
                    .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

            appointment.setDoctor(doctor);
            appointment.setPatient(patient);
            appointmentRepository.save(appointment);
            return ResponseEntity.ok(appointment);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 🔹 PUT update appointment
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAppointment(@PathVariable Long id, @RequestBody Appointment updatedAppointment) {
        return appointmentRepository.findById(id)
                .map(existing -> {
                    try {
                        Doctor doctor = doctorRepository.findById(updatedAppointment.getDoctor().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
                        Patient patient = patientRepository.findById(updatedAppointment.getPatient().getId())
                                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

                        existing.setDoctor(doctor);
                        existing.setPatient(patient);
                        existing.setDateTime(updatedAppointment.getDateTime());
                        existing.setStatus(updatedAppointment.getStatus());
                        appointmentRepository.save(existing);
                        return ResponseEntity.ok(existing);
                    } catch (Exception e) {
                        return ResponseEntity.badRequest().body(e.getMessage());
                    }
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 🔹 DELETE appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteAppointment(@PathVariable Long id) {
        return appointmentRepository.findById(id)
                .map(appointment -> {
                    appointmentRepository.delete(appointment);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
