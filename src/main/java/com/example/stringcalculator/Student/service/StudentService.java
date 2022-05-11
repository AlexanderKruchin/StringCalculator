package com.example.stringcalculator.Student.service;

import com.example.stringcalculator.Student.model.StudentModel;
import com.example.stringcalculator.Student.repo.StudentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepo studentRepo;

    @Autowired
    public StudentService(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    public StudentModel findById (Long id){
        return studentRepo.getById(id);
    }

    public List<StudentModel> findAll(){
        return studentRepo.findAll();
    }

    public void saveStudent(StudentModel student){
        studentRepo.save(student);
    }

    public void deleteById(Long id){
        studentRepo.deleteById(id);
    }
}
