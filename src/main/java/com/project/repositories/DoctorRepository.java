package com.project.repositories;

import com.project.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Doctor findByName(String name);
}
