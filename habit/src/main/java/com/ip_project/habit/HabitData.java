package com.ip_project.habit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "habit_data")
public class HabitData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "day_val") // Must match 'day_val' in your CREATE TABLE script
    private int dayVal;

    @Column(name = "month_name") // Must match 'month_name' in your CREATE TABLE script
    private String monthName;
    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "task_name")
    private String taskName;

    // Default Constructor (Required by JPA)
    public HabitData() {
    }

    // GETTERS AND SETTERS (Crucial for saving data!)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getMonthName() {
        return monthName;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public int getDayVal() {
        return dayVal;
    }

    public void setDayVal(int dayVal) {
        this.dayVal = dayVal;
    }
}