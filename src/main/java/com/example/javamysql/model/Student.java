package com.example.javamysql.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.Set;

@Entity
public class Student implements Comparable<Student> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    @NotNull(message = "Roll number cannot be null")
    @Positive(message = "Roll number must be positive")
    private int rollNumber;

    @NotBlank(message = "Full name cannot be empty")
    @Column(nullable = false)
    private String fullName;

    @Positive(message = "Age must be a positive number")
    private int age;

    @NotBlank(message = "Address cannot be empty")
    @Column(nullable = false)
    private String address;

    @Size(min = 4, max = 4, message = "Student must choose exactly 4 courses")
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "student_courses",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_code")
    )
    private Set<Course> courses;

    // Default constructor for JPA
    public Student() {
    }

    // All-args constructor
    public Student(int rollNumber, String fullName, int age, String address, Set<Course> courses) {
        this.rollNumber = rollNumber;
        this.fullName = fullName;
        this.age = age;
        this.address = address;
        this.courses = courses;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
