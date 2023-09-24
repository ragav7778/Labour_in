package com.example.laboursapp.Classes;

public class Request {
    String job_desc, job_type, name, email;
    int noofdays, noofemp, noofemphired, age, request_id;
    double salary, height, weight;

    public Request(String job_desc, String job_type, int noofdays, int noofemp,
                   int noofemphired, double salary,String name, int age,  double height,
                   double weight, int request_id, String email) {
        this.job_desc = job_desc;
        this.job_type = job_type;
        this.name = name;
        this.noofdays = noofdays;
        this.noofemp = noofemp;
        this.noofemphired = noofemphired;
        this.age = age;
        this.salary = salary;
        this.height = height;
        this.weight = weight;
        this.request_id = request_id;
        this.email=email;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    public String getJob_type() {
        return job_type;
    }

    public void setJob_type(String job_type) {
        this.job_type = job_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoofdays() {
        return noofdays;
    }

    public void setNoofdays(int noofdays) {
        this.noofdays = noofdays;
    }

    public int getNoofemp() {
        return noofemp;
    }

    public void setNoofemp(int noofemp) {
        this.noofemp = noofemp;
    }

    public int getNoofemphired() {
        return noofemphired;
    }

    public void setNoofemphired(int noofemphired) {
        this.noofemphired = noofemphired;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getRequest_id() {
        return request_id;
    }

    public void setRequest_id(int request_id) {
        this.request_id = request_id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
