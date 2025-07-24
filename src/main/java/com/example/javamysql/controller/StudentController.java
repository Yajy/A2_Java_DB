package com.example.javamysql.controller;

import com.example.javamysql.model.Student;
import com.example.javamysql.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * Add a new student
     */
    @PostMapping
    public String addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    /**
     * Delete a student by roll number
     */
    @DeleteMapping("/{rollNumber}")
    public String deleteStudent(@PathVariable int rollNumber) {
        return studentService.deleteStudent(rollNumber);
    }

    /**
     * List all students, with optional sort
     */
    @GetMapping
    public List<Student> getAllStudents(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {

        if (sortBy == null || sortBy.isBlank()) {
            return studentService.getAllStudents(); // default sort
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

        return studentService.getAllStudents(comparator);
    }

    /**
     * Save in-memory students to DB
     */
    @PostMapping("/save")
    public String saveToDB() {
        studentService.saveToDB();
        return "Students saved to DB";
    }

    /**
     * Load students from DB to in-memory
     */
    @PostMapping("/load")
    public String loadFromDB() {
        studentService.loadFromDB();
        return "Students loaded from DB";
    }
}
