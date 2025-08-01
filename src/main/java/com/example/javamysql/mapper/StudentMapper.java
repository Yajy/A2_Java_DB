package com.example.javamysql.mapper;

import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.model.Course;
import com.example.javamysql.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class StudentMapper {
    public static StudentDTO toDTO(Student student) {
        List<String> courseCodes = student.getCourses()
                .stream()
                .map(Course::getCode)
                .collect(Collectors.toList());

        return StudentDTO.builder()
                .rollNumber(student.getRollNumber())
                .fullName(student.getFullName())
                .age(student.getAge())
                .address(student.getAddress())
                .courses(courseCodes)
                .build();
    }

    public static Student toEntity(StudentDTO dto) {
        Student student = new Student();
        student.setRollNumber(dto.getRollNumber());
        student.setFullName(dto.getFullName());
        student.setAge(dto.getAge());
        student.setAddress(dto.getAddress());

        // Convert course codes to Course entities
        Set<Course> courses = dto.getCourses().stream()
                .map(code -> {
                    Course course = new Course();
                    course.setCode(code);
                    return course;
                })
                .collect(Collectors.toSet());
        student.setCourses(courses);

        return student;
    }
}
