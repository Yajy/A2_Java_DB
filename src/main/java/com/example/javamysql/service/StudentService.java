package com.example.javamysql.service;

import com.example.javamysql.dto.StudentDTO;
import com.example.javamysql.model.Student;
import java.util.Comparator;
import java.util.List;

public interface StudentService {
    void addStudent(StudentDTO student);
    void deleteStudent(int rollNumber);
    List<StudentDTO> getAllStudentsAsDTO();
    List<StudentDTO> getAllStudentsAsDTO(Comparator<Student> comparator);
    void saveToDB();
    void loadFromDB();
    void showQueue();

}
