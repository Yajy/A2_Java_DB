package com.example.javamysql.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.model.Student;
import com.example.javamysql.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;
import jakarta.validation.Valid;

@Slf4j
@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Add a new student
     */
    @PostMapping
    public ResponseEntity<String> addStudent(@Valid @RequestBody StudentDTO student) {
        studentService.addStudent(student);
        log.info("Adding student: {}", student);
        return ResponseEntity.ok("Student added successfully");
    }

    /**
     * Delete a student by roll number
     */
    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<String> deleteStudent(@PathVariable int rollNumber) {
        studentService.deleteStudent(rollNumber);
        return ResponseEntity.ok("Student deleted successfully");
    }


    @GetMapping
    public List<StudentDTO> getAllStudents(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {

        if (!StringUtils.isNotBlank(sortBy)) {
            return studentService.getAllStudentsAsDTO(); // default sort
        }

        Comparator<Student> comparator;

        switch (sortBy.toLowerCase()) {
            case "rollnumber":
                comparator = Comparator.comparing(Student::getRollNumber);
                break;
            case "age":
                comparator = Comparator.comparing(Student::getAge);
                break;
            case "address":
                comparator = Comparator.comparing(Student::getAddress, String.CASE_INSENSITIVE_ORDER);
                break;
            case "name":
            default:
                comparator = Comparator.comparing(Student::getFullName, String.CASE_INSENSITIVE_ORDER);
                break;
        }

        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }

        return studentService.getAllStudentsAsDTO(comparator);
    }

    /**
     * Save in-memory students to DB
     */
    @PostMapping("/save")
    public ResponseEntity<String> saveToDB() {
        studentService.saveToDB();
        return ResponseEntity.ok("Students saved to DB");
    }

    @GetMapping("/Queue")
    public ResponseEntity<String> getQueue() {
        studentService.showQueue();
        return ResponseEntity.ok("Queue is not implemented yet");
    }

    /**
     * Load students from DB to in-memory
     */
    @PostMapping("/load")
    public ResponseEntity<String> loadFromDB() {
        studentService.loadFromDB();
        return ResponseEntity.ok("Students loaded from DB");
    }
}
