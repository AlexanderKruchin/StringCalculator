package com.example.stringcalculator.Student.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;


@Entity
@Data
@Table(name = "students")
public class StudentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "full_name")
    private String fullName;
    @Column(name = "rank")
    private String rank;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "yearAdm")
    private Date yearAdm;
    @Column(name = "correctAnswers")
    private Integer correctAnswers;
    @Column(name = "incorrectAnswers")
    private Integer incorrectAnswers;
    @Column(name = "validAnswer")
    private Integer validAnswer;
    @Column(name = "invalidAnswers")
    private Integer invalidAnswers;

}
