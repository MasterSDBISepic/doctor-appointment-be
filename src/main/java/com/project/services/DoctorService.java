package com.project.services;

import com.project.dtos.DoctorDto;
import com.project.models.Doctor;
import com.project.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DoctorService {
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void addDoctorToDb(DoctorDto d) {
        Doctor doctor = Doctor.builder()
                .name(d.getName())
                .appointments(new ArrayList<>())
                .build();
        Map<String, Map<String, Integer>> scheduleMap = transformSchedule(d);
        doctor.setScheduleMap(scheduleMap);
        //System.out.println("doctor: ");
        //System.out.println(doctor);
        doctorRepository.save(doctor);
    }

    private Map<String, Map<String, Integer>> transformSchedule(DoctorDto d) {
        Map<String, Map<String, Integer>> scheduledMap = new HashMap<>();
        scheduledMap.put("monday", createDayMap(d.getStartMonday(), d.getEndMonday()));
        scheduledMap.put("tuesday", createDayMap(d.getStartTuesday(), d.getEndTuesday()));
        scheduledMap.put("wednesday", createDayMap(d.getStartWednesday(), d.getEndWednesday()));
        scheduledMap.put("thursday", createDayMap(d.getStartThursday(), d.getEndThursday()));
        scheduledMap.put("friday", createDayMap(d.getStartFriday(), d.getEndFriday()));
        scheduledMap.put("saturday", createDayMap(d.getStartSaturday(), d.getEndSaturday()));
        scheduledMap.put("sunday", createDayMap(d.getStartSunday(), d.getEndSunday()));
        return scheduledMap;
    }

    private Map<String, Integer> createDayMap(Integer start, Integer end) {
        Map<String, Integer> dayMap = new HashMap<>();
        dayMap.put("start", start);
        dayMap.put("end", end);
        return dayMap;
    }

    public List<Doctor> findAllDoctors() {
        return doctorRepository.findAll();
    }

    public void deleteDoctorById(Long id) {
        doctorRepository.deleteById(id);
    }

    public Doctor findById(Long id) {
        return doctorRepository.findById(id).orElse(new Doctor());
    }


    public List<Doctor> findAllDoctorsModifiedSchedule() {
        List<Doctor> doctors = doctorRepository.findAll();
        for (Doctor d: doctors) {
            d.setSchedule(modifySchedule(d.getScheduleMap()));
        }
        return doctors;
    }

    private String modifySchedule(Map<String, Map<String, Integer>> schedule) {
        StringBuilder s = new StringBuilder();
        Integer startHour = 0;
        Integer endHour =  0;
        if (schedule.get("monday") != null) {
            startHour = Integer.valueOf(schedule.get("monday").get("start"));
            endHour =  Integer.valueOf(schedule.get("monday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("MONDAY - NO PROGRAM ");
            else
                s.append("MONDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("tuesday") != null) {
            startHour = Integer.valueOf(schedule.get("tuesday").get("start"));
            endHour =  Integer.valueOf(schedule.get("tuesday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("TUESDAY - NO PROGRAM ");
            else
                s.append("TUESDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("wednesday") != null) {
            startHour = Integer.valueOf(schedule.get("wednesday").get("start"));
            endHour =  Integer.valueOf(schedule.get("wednesday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("WEDNESDAY - NO PROGRAM ");
            else
                s.append("WEDNESDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("thursday") != null) {
            startHour = Integer.valueOf(schedule.get("thursday").get("start"));
            endHour =  Integer.valueOf(schedule.get("thursday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("THURSDAY - NO PROGRAM ");
            else
                s.append("THURSDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("friday") != null) {
            startHour = Integer.valueOf(schedule.get("friday").get("start"));
            endHour =  Integer.valueOf(schedule.get("friday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("FRIDAY - NO PROGRAM ");
            else
                s.append("FRIDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("saturday") != null) {
            startHour = Integer.valueOf(schedule.get("saturday").get("start"));
            endHour =  Integer.valueOf(schedule.get("saturday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("SATURDAY - NO PROGRAM ");
            else
                s.append("SATURDAY " + startHour + "-" + endHour + "\n");
        }

        if (schedule.get("sunday") != null) {
            startHour = Integer.valueOf(schedule.get("sunday").get("start"));
            endHour =  Integer.valueOf(schedule.get("sunday").get("end"));

            if (startHour == 0 && endHour == 0)
                s.append("SUNDAY - NO PROGRAM ");
            else
                s.append("SUNDAY " + startHour + "-" + endHour + "\n");
        }
        return s.toString();
    }


}
