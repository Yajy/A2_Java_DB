package com.example.javamysql.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity // Tells JPA this class maps to DB table
public class Student implements Comparable<Student> {

    @Id // Primary key in DB
    private int rollNumber;

    @Column(nullable = false)
    private String fullName;

    private int age;

    @Column(nullable = false)
    private String address;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_roll_number"),
            inverseJoinColumns = @JoinColumn(name = "course_code")
    )
    private Set<Course> courses;

    public Student() {
    }

    public Student(int rollNumber, String fullName, int age, String address, Set<Course> courses) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.courses = courses;
    }

    // Getters & setters (generate in IntelliJ: Alt+Insert / Cmd+N)
    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    // Default sorting: by name, then roll number
    @Override
    public int compareTo(Student other) {
        int nameCompare = this.fullName.compareToIgnoreCase(other.fullName);
        if (nameCompare != 0) {
            return nameCompare;
        }
        return Integer.compare(this.rollNumber, other.rollNumber);
    }
}
