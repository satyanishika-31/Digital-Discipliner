package com.deploy.demo; // Must be the first line!

public class HabitRequest {
    private String email;
    private String taskName;
    private String month;
    private int day;

    // Default Constructor (Required by Spring Boot)
    public HabitRequest() {}

    // Getters and Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTaskName() { return taskName; }
    public void setTaskName(String taskName) { this.taskName = taskName; }
    public String getMonth() { return month; }
    public void setMonth(String month) { this.month = month; }
    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }
}