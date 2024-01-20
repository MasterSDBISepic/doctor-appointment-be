package com.project.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentDto {
    private Long doctorId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate appointmentDate;
}
