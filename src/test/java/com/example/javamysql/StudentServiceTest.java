package com.example.javamysql;

import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.exception.DuplicateStudentException;
import com.example.javamysql.exception.StudentNotFoundException;
import com.example.javamysql.repository.StudentRepository;
import com.example.javamysql.service.StudentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private List<String> createValidCourses() {
        return Arrays.asList("A", "B", "C", "D");
    }

    @Test
    void shouldAddValidStudent() {
        StudentDTO student = StudentDTO.builder()
                .rollNumber(1)
                .fullName("John Doe")
                .age(20)
                .address("123 Main St")
                .courses(createValidCourses())
                .build();

        studentService.addStudent(student);

        List<StudentDTO> all = studentService.getAllStudentsAsDTO();
        assertEquals(1, all.size());
        StudentDTO saved = all.get(0);
        assertEquals(1, saved.getRollNumber());
        assertEquals("John Doe", saved.getFullName());
        assertEquals(20, saved.getAge());
        assertEquals("123 Main St", saved.getAddress());
        assertEquals(4, saved.getCourses().size());
    }

    @Test
    void shouldThrowWhenAddingStudentWithEmptyName() {
        StudentDTO student = StudentDTO.builder()
                .rollNumber(2)
                .fullName(" ")
                .age(20)
                .address("123 Main St")
                .courses(createValidCourses())
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                studentService.addStudent(student));

        assertEquals("Full name cannot be empty", exception.getMessage());
        assertEquals(0, studentService.getAllStudentsAsDTO().size());
    }

    @Test
    void shouldThrowWhenAddingDuplicateRollNumber() {
        StudentDTO student1 = StudentDTO.builder()
                .rollNumber(3)
                .fullName("Alice")
                .age(21)
                .address("Street 1")
                .courses(createValidCourses())
                .build();
        StudentDTO student2 = StudentDTO.builder()
                .rollNumber(3)
                .fullName("Bob")
                .age(22)
                .address("Street 2")
                .courses(createValidCourses())
                .build();

        studentService.addStudent(student1);

        assertThrows(DuplicateStudentException.class, () ->
                studentService.addStudent(student2));

        assertEquals(1, studentService.getAllStudentsAsDTO().size());
    }

    @Test
    void shouldDeleteExistingStudent() {
        StudentDTO student = StudentDTO.builder()
                .rollNumber(4)
                .fullName("Charlie")
                .age(22)
                .address("Street 3")
                .courses(createValidCourses())
                .build();

        studentService.addStudent(student);
        assertEquals(1, studentService.getAllStudentsAsDTO().size());

        studentService.deleteStudent(4);

        List<StudentDTO> remaining = studentService.getAllStudentsAsDTO();
        assertEquals(0, remaining.size());
        assertTrue(remaining.stream().noneMatch(s -> s.getRollNumber() == 4));
    }

    @Test
    void shouldThrowWhenDeletingNonExistentStudent() {
        assertThrows(StudentNotFoundException.class, () ->
                studentService.deleteStudent(999));
    }
}
