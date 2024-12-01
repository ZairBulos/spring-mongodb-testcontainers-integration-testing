package com.zair.services.impl;

import com.zair.entities.Student;
import com.zair.repositories.StudentRepository;
import com.zair.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository repository;

    @Override
    public Optional<Student> findStudentByName(String name) {
        return repository.findByName(name);
    }

    @Override
    public List<Student> findStudentsByAgeGreaterThan(Integer age) {
        return repository.findByAgeGreaterThan(age);
    }

    @Override
    public List<Student> findStudentsByAgeLessThan(Integer age) {
        return repository.findByAgeLessThan(age);
    }

    @Override
    public void saveAllStudents(List<Student> students) {
        repository.saveAll(students);
    }
}
