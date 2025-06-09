package com.clinic.appointment.repository;

import com.clinic.appointment.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT COUNT(a) > 0 FROM Appointment a WHERE a.doctor.id = :doctorId AND a.dateTime = :dateTime")
    boolean existsByDoctorAndDateTime(@Param("doctorId") Long doctorId, @Param("dateTime") LocalDateTime dateTime);
}