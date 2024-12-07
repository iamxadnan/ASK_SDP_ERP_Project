package com.klef.jfsd.sdpproject.models;
import jakarta.persistence.*;

@Entity
@Table(name = "periods")
public class Period {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int periodId;

    @Column(name = "start_time", nullable = false)
    private String startTime; // Example: "09:00 AM"

    @Column(name = "end_time", nullable = false)
    private String endTime; // Example: "09:45 AM"

    // Constructors
    public Period() {}

    public Period(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getPeriodId() {
        return periodId;
    } 

    public void setPeriodId(int periodId) {
        this.periodId = periodId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}

