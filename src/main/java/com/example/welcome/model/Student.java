package com.example.welcome.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Student")
public class Student {

    @Id
    private Long id;

    private String name;
    private int age;

    // Getters and setters
    public Long getId() {return id;}
    public void setId(Long id){ this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age;}
}
