package org.example.tralalelotralala;

import org.json.JSONObject;

public class Employee {
    //add id
    private int id = 0;
    private String name;
    private String surname;
    private double salary;
    private String sex;
    private String jobType;
    private String role;

    public Employee(int id,String name, String surname, double salary, String sex, String jobType, String role) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.salary = salary;
        this.sex = sex;
        this.jobType = jobType;
        this.role = role;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public double getSalary() {
        return salary;
    }

    public String getSex() {
        return sex;
    }

    public String getJobType() {
        return jobType;
    }

    public String getRole() {
        return role;
    }
    public int getId() {
        return id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSurname(String surname){
        this.surname = surname;
    }
    public void setSalary(double salary){
        this.salary = salary;
    }
    public void setSex (String sex){
        this.sex = sex;
    }
    public void setJobType(String jobType){
        this.jobType = jobType;
    }
    public void setRole(String role){
        this.role = role;
    }
}
