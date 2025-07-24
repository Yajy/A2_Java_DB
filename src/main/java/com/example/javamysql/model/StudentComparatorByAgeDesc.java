package com.example.javamysql.model;

import java.util.Comparator;

public class StudentComparatorByAgeDesc implements Comparator<Student> {
    @Override
    public int compare(Student s1, Student s2) {
        return Integer.compare(s2.getAge(), s1.getAge()); // descending
    }
}
