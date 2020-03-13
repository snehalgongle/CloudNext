package com.snake.cloudnext;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("designation")
    @Expose
    private String designation;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("salary")
    @Expose
    private String salary;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("experience")
    @Expose
    private String experience;

    public Data(int id,String name, String designation, String gender, String salary) {
        this.id=id;
        this.name = name;
        this.designation = designation;
        this.gender = gender;
        this.salary = salary;
    }

    public Data(int id,String name, String designation, String gender, String salary,String experience) {
        this.id=id;
        this.name = name;
        this.designation = designation;
        this.gender = gender;
        this.salary = salary;
        this.experience=experience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }
}
