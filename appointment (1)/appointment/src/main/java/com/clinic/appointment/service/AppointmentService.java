package com.clinic.appointment.service;

import com.clinic.appointment.model.Appointment;
import com.clinic.appointment.model.Doctor;
import com.clinic.appointment.model.Patient;
import com.clinic.appointment.repository.AppointmentRepository;
import com.clinic.appointment.repository.DoctorRepository;
import com.clinic.appointment.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private PatientRepository patientRepository;

    public Appointment bookAppointment(Appointment appointment) {
        boolean exists = appointmentRepository.existsByDoctorAndDateTime(
                appointment.getDoctor().getId(),
                appointment.getDateTime()
        );

        if (exists) {
            throw new IllegalArgumentException("This doctor already has an appointment at this time.");
        }

        // Fetch full Doctor and Patient
        Doctor doctor = doctorRepository.findById(appointment.getDoctor().getId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found"));
        Patient patient = patientRepository.findById(appointment.getPatient().getId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found"));

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }
}