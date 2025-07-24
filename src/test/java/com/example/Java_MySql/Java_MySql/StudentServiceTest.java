package com.example.Java_MySql.Java_MySql;

import com.example.Java_MySql.model.Course;
import com.example.Java_MySql.model.Student;
import com.example.Java_MySql.repository.StudentRepository;
import com.example.Java_MySql.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class StudentServiceTest {

    private StudentService studentService;


    @BeforeEach
    void setUp() {
        // Mock repository because we are only testing in-memory logic
        StudentRepository mockRepo = Mockito.mock(StudentRepository.class);
        studentService = new StudentService();
        studentService.setStudentRepository(mockRepo);
    }

    @Test
    void testAddValidStudent() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("A", "Course A"));
        courses.add(new Course("B", "Course B"));
        courses.add(new Course("C", "Course C"));
        courses.add(new Course("D", "Course D"));

        Student student = new Student(1, "John Doe", 20, "123 Main St", courses);

        String result = studentService.addStudent(student);

        assertEquals("Student added successfully", result);
        assertEquals(1, studentService.getAllStudents().size());
    }

    @Test
    void testAddStudentWithEmptyName() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("A", "Course A"));
        courses.add(new Course("B", "Course B"));
        courses.add(new Course("C", "Course C"));
        courses.add(new Course("D", "Course D"));

        Student student = new Student(2, " ", 20, "123 Main St", courses);

        String result = studentService.addStudent(student);

        assertEquals("Full name cannot be empty", result);
        assertEquals(0, studentService.getAllStudents().size());
    }

    @Test
    void testAddDuplicateRollNumber() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("A", "Course A"));
        courses.add(new Course("B", "Course B"));
        courses.add(new Course("C", "Course C"));
        courses.add(new Course("D", "Course D"));

        Student student1 = new Student(3, "Alice", 21, "Street 1", courses);
        Student student2 = new Student(3, "Bob", 22, "Street 2", courses); // same rollNumber

        studentService.addStudent(student1);
        String result = studentService.addStudent(student2);

        assertEquals("Student with this roll number already exists", result);
        assertEquals(1, studentService.getAllStudents().size());
    }
}
