package com.project.controllers;

import com.project.dtos.AppointmentDto;
import com.project.dtos.AppointmentWithHoursDto;
import com.project.models.Appointment;
import com.project.services.AppointmentService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
public class AdminController {
    private final AppointmentService appointmentService;

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/adminDashboard";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/appointment/add")
    public String addAppointmentPage(Model model) {
        model = appointmentService.prepareModelForForm(model);
        return "admin/add-appointment";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/appointments/all")
    public String showAllAppointments(Model model) {
        List<Appointment> appointments = appointmentService.findAllAppointments();
        model.addAttribute("appointments", appointments);
        return "admin/appointments";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/appointment/delete/{id}")
    public String deleteAppointmentById(@PathVariable Long id, Model model) {
        System.out.println("Delete appointment with id: "+ id);
        appointmentService.deleteAppointmentById(id);
        List<Appointment> appointments = appointmentService.findAllAppointments();
        model.addAttribute("appointments", appointments);
        return "admin/appointments";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/appointment/accept/{id}")
    public String acceptAppointmentById(@PathVariable Long id, Model model) {
        appointmentService.acceptAppointmentById(id);
        List<Appointment> appointments = appointmentService.findAllAppointments();
        model.addAttribute("appointments", appointments);
        return "admin/appointments";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/appointments/add")
    public String getAvailableHours(@ModelAttribute("appointment") AppointmentDto appointmentDto, Model model) throws ParseException {
        List<LocalDateTime> hours = appointmentService.getAvailableData(appointmentDto);
        model = appointmentService.prepareModelForSecondForm(model, appointmentDto, hours);
        return "admin/appointment-secondpage";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/appointments/final/add")
    public String appointmentAdded(@ModelAttribute("appointment") AppointmentWithHoursDto appointment, Model model) throws ParseException {
        //System.out.println("ce am primit: ");
        //System.out.println(appointment);
        appointmentService.saveAppointment(appointment);
        model.addAttribute("name", appointment.getName());
        model.addAttribute("date", appointment.getStartDate());
        model.addAttribute("doctor", appointment.getDoctorName());
        return "admin/appointment-final";
    }

}
