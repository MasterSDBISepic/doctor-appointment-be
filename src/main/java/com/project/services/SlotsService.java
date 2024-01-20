package com.project.services;

import com.project.dtos.AppointmentDto;
import com.project.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class SlotsService {

    private final AppointmentRepository appointmentRepository;

    public List<LocalDateTime> getAllSlots(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {

        switch (date.getDayOfWeek()) {
            case MONDAY:
                return computeAllSlotsForMonday(date, doctorSchedule);
            case TUESDAY:
                return computeAllSlotsForTuesday(date, doctorSchedule);
            case WEDNESDAY:
                return computeAllSlotsForWednesday(date, doctorSchedule);
            case THURSDAY:
                return computeAllSlotsForThursday(date, doctorSchedule);
            case FRIDAY:
                return computeAllSlotsForFriday(date, doctorSchedule);
            case SATURDAY:
                return computeAllSlotsForSaturday(date, doctorSchedule);
            case SUNDAY:
                return computeAllSlotsForSunday(date, doctorSchedule);

            default:
                List<LocalDateTime> listt = new ArrayList<>();
                return listt;

        }
    }

    private List<LocalDateTime> generateIntervals(LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<LocalDateTime> intervals = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime current = LocalDateTime.of(date, startTime);
        LocalDateTime endDateTime = LocalDateTime.of(date, endTime);

        while (current.isBefore(endDateTime)) {
            intervals.add(current);
            current = current.plusMinutes(30);
        }

        return intervals;
    }

    private List<LocalDateTime> computeAllSlotsForSunday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("sunday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("sunday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForSaturday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("saturday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("saturday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForFriday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("friday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("friday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForThursday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("thursday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("thursday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForWednesday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("wednesday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("wednesday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForTuesday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("tuesday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("tuesday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    private List<LocalDateTime> computeAllSlotsForMonday(LocalDate date, Map<String, Map<String, Integer>> doctorSchedule) {
        Integer startHour = Integer.valueOf(doctorSchedule.get("monday").get("start"));
        Integer endHour =  Integer.valueOf(doctorSchedule.get("monday").get("end"));
        LocalTime startTime = LocalTime.of(startHour, 0);
        LocalTime endTime = LocalTime.of(endHour, 0);
        return generateIntervals(date, startTime, endTime);
    }

    public List<LocalDateTime> computeFreeSlots(List<LocalDateTime> busySlots, AppointmentDto appointmentDto,
                                                Map<String, Map<String, Integer>> doctorSchedule) {
        List<LocalDateTime> allSlots = getAllSlots(appointmentDto.getAppointmentDate(), doctorSchedule);
        System.out.println("all slots: ");
        System.out.println(allSlots);
        List<LocalDateTime> freeSlots = new ArrayList<>();
        for (LocalDateTime slot: allSlots) {
            if (!busySlots.contains(slot)) {
                freeSlots.add(slot);
            }
        }
        return freeSlots;
    }

    public List<LocalDateTime> getBusySlots(Long doctorId) {
        return appointmentRepository.findDoctorsAppointmentsById(doctorId);
    }
}
