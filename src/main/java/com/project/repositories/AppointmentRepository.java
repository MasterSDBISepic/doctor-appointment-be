package com.project.repositories;

import com.project.models.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    @Query("SELECT a.startDate FROM Appointment a WHERE a.doctor.id = :doctorId")
    List<LocalDateTime> findDoctorsAppointmentsById(@Param("doctorId") Long doctorId);
}
