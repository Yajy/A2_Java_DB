package com.example.javamysql.dto;

import java.util.Set;

public class StudentDTO {
    private int rollNumber;
    private String fullName;
    private int age;
    private String address;
    private Set<String> courses;

    // Constructor
    public StudentDTO(int rollNumber, String fullName, int age, String address, Set<String> courses) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.courses = courses;
    }

    // Getters & setters
    public int getRollNumber() {
        return rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public int getAge() {
        return age;
    }

    public String getAddress() {
        return address;
    }

    public Set<String> getCourses() {
        return courses;
    }
}
