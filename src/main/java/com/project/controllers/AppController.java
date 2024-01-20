package com.project.controllers;

import com.project.dtos.AppointmentDto;
import com.project.dtos.AppointmentWithHoursDto;
import com.project.services.AppointmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class AppController {

    private final AppointmentService appointmentService;

    public AppController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping("/user")
    public String mainPage() {
        return "user/main";
    }

    @GetMapping("/user/appointment/add")
    public String addAppointmentPage(Model model) {
        model = appointmentService.prepareModelForForm(model);
        return "user/add-appointment";
    }

    @PostMapping("/user/appointments/add")
    public String getAvailableHours(@ModelAttribute("appointment") AppointmentDto appointmentDto, Model model) throws ParseException {
        List<LocalDateTime> hours = appointmentService.getAvailableData(appointmentDto);
        model = appointmentService.prepareModelForSecondForm(model, appointmentDto, hours);
        return "user/appointment-secondpage";
    }

    @PostMapping("/user/appointments/final/add")
    public String appointmentAdded(@ModelAttribute("appointment") AppointmentWithHoursDto appointment, Model model) throws ParseException {
        //System.out.println("ce am primit: ");
        //System.out.println(appointment);
        appointmentService.saveAppointment(appointment);
        model.addAttribute("name", appointment.getName());
        model.addAttribute("date", appointment.getStartDate());
        model.addAttribute("doctor", appointment.getDoctorName());
        return "user/appointment-final";
    }

}
