package com.example.stringcalculator.Student.controller;


import com.example.stringcalculator.Student.model.StudentModel;
import com.example.stringcalculator.Student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public String findAll(Model model){
        List<StudentModel> students = studentService.findAll();
        model.addAttribute("students",students);
        return "student-list";
    }

    @GetMapping("/student-create")
    public String createStudentForm(StudentModel student){
        return "student-create";
    }

    @PostMapping("/student-create")
    public String createStudent(StudentModel student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }

    @GetMapping("student-delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id){
        studentService.deleteById(id);
        return "redirect:/students";
    }

    @GetMapping("/student-update/{id}")
    public String updateStudentForm(@PathVariable("id") Long id, Model model){
        StudentModel student = studentService.findById(id);
        model.addAttribute("student", student);
        return "student-update";
    }

    @PostMapping("/student-update")
    public String updateStudent(StudentModel student){
        studentService.saveStudent(student);
        return "redirect:/students";
    }


}
