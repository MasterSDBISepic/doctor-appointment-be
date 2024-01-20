package com.project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "appointments")
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String phone;

    @ManyToOne // Many appointments can be associated with one doctor
//    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    private String doctorName;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime startDate; // 30 minute

    private String status;
}
