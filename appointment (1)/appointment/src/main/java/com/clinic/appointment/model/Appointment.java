package com.clinic.appointment.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity

public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Patient patient;

    private LocalDateTime dateTime;
    private String status; // PENDING, CONFIRMED, CANCELLED

}