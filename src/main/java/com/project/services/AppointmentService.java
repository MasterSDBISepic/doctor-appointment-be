package com.project.services;

import com.project.dtos.AppointmentDto;
import com.project.dtos.AppointmentWithHoursDto;
import com.project.models.Appointment;
import com.project.models.Doctor;
import com.project.repositories.AppointmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final SlotsService slotsService;

    public List<Appointment> findAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Model prepareModelForForm(Model model) {
        model.addAttribute("allDoctors", doctorService.findAllDoctors());
        model.addAttribute("appointment", new AppointmentDto());
        return model;
    }

    public List<LocalDateTime> getAvailableData(AppointmentDto appointmentDto) throws ParseException {
    //appointmentDto are: numele medicului si data la care trb sa se prezinte
    //doctor.schedule: {"monday" : {"start": 9, "end": 13}, "tuesday" : {"start": 9, "end": 13}, "wednesday" : {"start": 9, "end": 13}, "thursday": {"start": 9, "end": 13}, "friday": {"start": 9, "end": 13}, "saturday": {"start": 9, "end": 12}, "sunday": {"start": 0, "end": 0}}

        Doctor doctor =  doctorService.findById(appointmentDto.getDoctorId());
        Map<String, Map<String, Integer>> schedule = doctor.getScheduleMap();
        List<LocalDateTime> busySlots = slotsService.getBusySlots(appointmentDto.getDoctorId());
        List<LocalDateTime> freeSlots = slotsService.computeFreeSlots(busySlots, appointmentDto, schedule);

        return freeSlots;
    }

    public Model prepareModelForSecondForm(Model model, AppointmentDto appointmentDto, List<LocalDateTime> hours) {
        Doctor doctor = doctorService.findById(appointmentDto.getDoctorId());
        String doctorName = doctor.getName();
        model.addAttribute("doctorName", doctorName);
        model.addAttribute("date", appointmentDto.getAppointmentDate());
        model.addAttribute("freeHours", hours);

        AppointmentWithHoursDto appointmentWithHoursDto = new AppointmentWithHoursDto();
        appointmentWithHoursDto.setDoctorName(doctorName);
        appointmentWithHoursDto.setDate(appointmentDto.getAppointmentDate());
        appointmentWithHoursDto.setDoctor_id(appointmentDto.getDoctorId());
        appointmentWithHoursDto.setDate(appointmentDto.getAppointmentDate());
        appointmentWithHoursDto.setStatus("pending");
        appointmentWithHoursDto.setFreeHours(hours);
//        System.out.println("appointmentWithHoursDto: ");
//        System.out.println(appointmentWithHoursDto);
        model.addAttribute("appointment", appointmentWithHoursDto);
        return model;
    }

    public void saveAppointment(AppointmentWithHoursDto appointmentWithHoursDto) {
       Doctor doctorById = doctorService.findById(appointmentWithHoursDto.getDoctor_id());
       Appointment appointment = Appointment.builder()
                .name(appointmentWithHoursDto.getName())
                .phone(appointmentWithHoursDto.getPhone())
                .doctorName(appointmentWithHoursDto.getDoctorName())
                .startDate(appointmentWithHoursDto.getStartDate())
                .status(appointmentWithHoursDto.getStatus())
                .build();
        appointment.setDoctor(doctorById); // set the doctor for the appointment
        appointmentRepository.save(appointment);
    }

    public void deleteAppointmentById(Long id) {
        appointmentRepository.deleteById(id);
    }

    public void acceptAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus("accepted");
            appointmentRepository.save(appointment);
        } else {
            System.out.println("appointment not found");
        }
    }
}
