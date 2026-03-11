package com.example.javamysql.service;

import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.mapper.StudentMapper;
import com.example.javamysql.model.Student;
import com.example.javamysql.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.example.javamysql.exception.StudentNotFoundException;
import com.example.javamysql.exception.DuplicateStudentException;


import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    // In-memory storage (default sorted by name then roll number because Student implements Comparable)
    private final Set<Student> students = new TreeSet<>();

    @Override
    public void addStudent(StudentDTO student) {
        log.info("Adding student: {}", student.getFullName());
        // Validate student name
        if (student.getFullName() == null || student.getFullName().trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be empty");
        }

        // Business rule: rollNumber must be unique
        boolean exists = students.stream()
                .anyMatch(s -> s.getRollNumber() == student.getRollNumber());
        if (exists) {
            throw new DuplicateStudentException("Student with roll number " + student.getRollNumber() + " already exists");
        }

        Student entity = StudentMapper.toEntity(student);
        students.add(entity);
    }



    @Override
    public void deleteStudent(int rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber() == rollNumber);
        if (!removed) {
            throw new StudentNotFoundException("Student with roll number " + rollNumber + " not found");
        }
    }

    @Override
    public List<StudentDTO> getAllStudentsAsDTO() {
        return students.stream()
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> getAllStudentsAsDTO(Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .map(StudentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveToDB() {
        log.info("Saving {} students to DB", students.size());
        studentRepository.saveAll(students);
    }

    @Override
    public void loadFromDB() {
        log.info("Loading students from DB");
        students.clear();
        students.addAll(studentRepository.findAll());
    }

    @Override
    public void showQueue() {
        log.info("Current students in memory:");
        students.forEach(student -> log.info("Roll Number: {}, Name: {}, Id: {} ", student.getRollNumber(), student.getFullName(), student.getId()));
    }


}


/*
    1. error handling from backend to frontend
    2. proper error messages in frontend
    3. show data in table format in frontend
    4. in memory caching for valid courses
 */