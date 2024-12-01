package com.zair.controllers;

import com.zair.entities.Student;
import com.zair.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService service;

    @GetMapping("/{name}")
    public Student getStudentByName(@PathVariable String name) {
        return service.findStudentByName(name).get();
    }

    @GetMapping("/agt/{age}")
    public List<Student> getStudentByAgeGreaterThan(@PathVariable Integer age) {
        return service.findStudentsByAgeGreaterThan(age);
    }

    @GetMapping("/alt/{age}")
    public List<Student> getStudentByAgeLessThan(@PathVariable Integer age) {
        return service.findStudentsByAgeLessThan(age);
    }
}
