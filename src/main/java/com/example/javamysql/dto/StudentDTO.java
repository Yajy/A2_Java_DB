package com.example.javamysql.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class StudentDTO {
    @Min(1)
    private int rollNumber;
// list to courses
    @NotBlank
    private String fullName;

    @Min(1)
    private int age;

    @NotBlank
    private String address;

    @Size(min=4, max=4)
    private List<String> courses;

    // Constructor, getters, setters
//    public StudentDTO(int rollNumber, String fullName, int age, String address, Set<String> courses) {
//        this.rollNumber = rollNumber;
//        this.fullName = fullName;
//        this.age = age;
//        this.address = address;
//        this.courses = courses;
//    }

//    public StudentDTO() {}
//
//    // Getters & setters
//    public int getRollNumber() {
//        return rollNumber;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public Set<String> getCourses() {
//        return courses;
//    }
}
