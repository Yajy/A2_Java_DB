package com.example.javamysql;

import com.example.javamysql.model.Course;
import com.example.javamysql.model.Student;
import com.example.javamysql.repository.StudentRepository;
import com.example.javamysql.service.StudentServiceImpl;
import com.example.javamysql.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Set<Course> createValidCourses() {
        Set<Course> courses = new HashSet<>();
        courses.add(new Course("A", "Course A"));
        courses.add(new Course("B", "Course B"));
        courses.add(new Course("C", "Course C"));
        courses.add(new Course("D", "Course D"));
        return courses;
    }

    @Test
    void shouldAddValidStudent() {
        Student student = new Student(1, "John Doe", 20, "123 Main St", createValidCourses());

        //studentService.addStudent(student);

        assertEquals(1, studentService.getAllStudentsAsDTO().size());
    }

    @Test
    void shouldThrowWhenAddingStudentWithEmptyName() {
        Student student = new Student(2, " ", 20, "123 Main St", createValidCourses());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            //studentService.addStudent(student);
        });

        assertEquals("Full name cannot be empty", exception.getMessage());
        assertEquals(0, studentService.getAllStudentsAsDTO().size());
    }

    @Test
    void shouldThrowWhenAddingDuplicateRollNumber() {
        Student student1 = new Student(3, "Alice", 21, "Street 1", createValidCourses());
        Student student2 = new Student(3, "Bob", 22, "Street 2", createValidCourses()); // same rollNumber

        //studentService.addStudent(student1);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            //studentService.addStudent(student2);
        });

        assertEquals("Student with this roll number already exists", exception.getMessage());
        assertEquals(1, studentService.getAllStudentsAsDTO().size());
    }
}
