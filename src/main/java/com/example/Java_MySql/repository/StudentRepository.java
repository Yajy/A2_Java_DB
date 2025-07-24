package com.example.Java_MySql.repository;

import com.example.Java_MySql.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}
