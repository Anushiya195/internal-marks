package com.ims.model;

public class Student {
    private int id;
    private String registerNumber;
    private String fullName;
    private int deptId;
    private int semester;

    public Student() {
    }

    public Student(String registerNumber, String fullName, int deptId, int semester) {
        this.registerNumber = registerNumber;
        this.fullName = fullName;
        this.deptId = deptId;
        this.semester = semester;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRegisterNumber() {
        return registerNumber;
    }

    public void setRegisterNumber(String registerNumber) {
        this.registerNumber = registerNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getDeptId() {
        return deptId;
    }

    public void setDeptId(int deptId) {
        this.deptId = deptId;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
