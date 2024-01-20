package com.project.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String schedule;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="doctor")
    private List<Appointment> appointments; //busySlots

    @Transient
    public Map<String, Map<String, Integer>> getScheduleMap() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(schedule, new TypeReference<Map<String, Map<String, Integer>>>() {});
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transient
    public void setScheduleMap(Map<String, Map<String, Integer>> scheduleMap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            this.schedule = objectMapper.writeValueAsString(scheduleMap);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // Handle the exception according to your needs
        }
    }

}
