package com.project.controllers;

import com.project.dtos.DoctorDto;
import com.project.models.Doctor;
import com.project.services.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/doctor/add")
    public String showAddDoctorForm(Model model) {
        model.addAttribute("doctor", new DoctorDto());
        return "admin/add-doctor";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/doctors/all")
    public String findAllDoctors(Model model) {
        List<Doctor> doctors = doctorService.findAllDoctorsModifiedSchedule();
        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/admin/doctors/add")
    public String addDoctor(@ModelAttribute DoctorDto doctor, Model model) {
        //System.out.println("Doctor to be added");
        //System.out.println(doctor);
        doctorService.addDoctorToDb(doctor);
        List<Doctor> doctors = doctorService.findAllDoctorsModifiedSchedule();
        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/admin/doctor/{id}")
    public String deleteDoctorById(@PathVariable Long id, Model model) {
        System.out.println("delete doctor with id "+ id);
        doctorService.deleteDoctorById(id);
        List<Doctor> doctors = doctorService.findAllDoctorsModifiedSchedule();
        model.addAttribute("doctors", doctors);
        return "admin/doctors";
    }


}
