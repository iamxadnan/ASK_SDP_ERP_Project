package com.klef.jfsd.sdpproject.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "notification_table")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String mesg;
    
    @Column(name="PostedTime", nullable=false, length=20)
    private String postedTime;

    @Column(name = "scheduled_time", nullable = true)
    private LocalDateTime scheduledTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role1 role;

    // Enum for defining roles
    public enum Role1 {
        FACULTY,
        STUDENT,
        ADMIN
    }

    @PrePersist
    protected void onCreate() {
        this.postedTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    // Getters and setters
    public String getPostedTime() {
        return postedTime;
    }

    public void setPostedTime(String postedTime) {
        this.postedTime = postedTime;
    }

    public LocalDateTime getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(LocalDateTime scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMesg() {
        return mesg;
    }

    public void setMesg(String mesg) {
        this.mesg = mesg;
    }

    public Role1 getRole() {
        return role;
    }

    public void setRole(Role1 role) {
        this.role = role;
    }
}