package com.example.stringcalculator.Student.repo;

import com.example.stringcalculator.Student.model.StudentModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepo extends JpaRepository<StudentModel, Long> {
}

