package com.example.laboursapp.Classes;

public class Work {
    String job_desc, job_type;
    int noofdays, noofemp, noofemphired, work_id;
    double salary;

    public Work(String job_desc, String job_type, int noofdays, int noofemp, int noofemphired,
                double salary, int work_id) {
        this.job_desc = job_desc;
        this.job_type = job_type;
        this.noofdays = noofdays;
        this.noofemp = noofemp;
        this.noofemphired = noofemphired;
        this.salary = salary;
        this.work_id = work_id;
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

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getWork_id() {
        return work_id;
    }

    public void setWork_id(int work_id) {
        this.work_id = work_id;
    }
}
