package com.example.Java_MySql.service;


import com.example.Java_MySql.model.Student;
import com.example.Java_MySql.model.Course;
import com.example.Java_MySql.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private TreeSet<Student> students;

    @Autowired
    private StudentRepository studentRepository;

    public StudentService() {
        // Default sorting: by Student.compareTo()
        this.students = new TreeSet<>();
    }

    public void setStudentRepository(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }


    /**
     * Load data from DB into memory when app starts
     */
    public void loadFromDB() {
        List<Student> dbStudents = studentRepository.findAll();
        students.clear();
        students.addAll(dbStudents);
    }

    /**
     * Save in-memory students to DB
     */
    public void saveToDB() {
        studentRepository.saveAll(students);
    }

    /**
     * Add student to in-memory collection after validation
     */
    public String addStudent(Student student) {
        // Validate fields
        if (student.getFullName() == null || student.getFullName().trim().isEmpty()) {
            return "Full name cannot be empty";
        }
        if (student.getAddress() == null || student.getAddress().trim().isEmpty()) {
            return "Address cannot be empty";
        }
        if (student.getAge() <= 0) {
            return "Age must be a positive integer";
        }
        if (student.getRollNumber() <= 0) {
            return "Roll number must be a positive integer";
        }
        if (student.getCourses() == null || student.getCourses().size() != 4) {
            return "Student must choose exactly 4 courses";
        }
        // Check uniqueness of roll number
        boolean exists = students.stream()
                .anyMatch(s -> s.getRollNumber() == student.getRollNumber());
        if (exists) {
            return "Student with this roll number already exists";
        }

        students.add(student);
        return "Student added successfully";
    }

    /**
     * Delete student by roll number
     */
    public String deleteStudent(int rollNumber) {
        boolean removed = students.removeIf(s -> s.getRollNumber() == rollNumber);
        if (removed) {
            return "Student deleted successfully";
        } else {
            return "Student with roll number " + rollNumber + " not found";
        }
    }

    /**
     * Get all students sorted by default (name + roll number)
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /**
     * Get all students sorted by custom comparator
     */
    public List<Student> getAllStudents(Comparator<Student> comparator) {
        List<Student> sortedList = new ArrayList<>(students);
        sortedList.sort(comparator);
        return sortedList;
    }
}
