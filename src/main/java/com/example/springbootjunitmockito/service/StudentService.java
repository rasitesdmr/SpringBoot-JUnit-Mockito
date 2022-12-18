package com.example.springbootjunitmockito.service;

import com.example.springbootjunitmockito.model.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    void addStudent(Student student);

    void deleteStudent(Long studentId);
}
