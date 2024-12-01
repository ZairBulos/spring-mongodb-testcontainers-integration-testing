package com.zair.services;

import com.zair.entities.Student;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    Optional<Student> findStudentByName(String name);
    List<Student> findStudentsByAgeGreaterThan(Integer age);
    List<Student> findStudentsByAgeLessThan(Integer age);
    void saveAllStudents(List<Student> students);
}
