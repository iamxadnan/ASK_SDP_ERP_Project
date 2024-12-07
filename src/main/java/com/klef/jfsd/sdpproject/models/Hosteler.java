package com.klef.jfsd.sdpproject.models;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
@Table(name = "hosteler")
public class Hosteler {
    @Id
    @Column(name = "uid")
    private int userid;

    @Column(name = "room_id", nullable = true)
    private int roomId;

    @Column(name = "admission_date", nullable = false, updatable = false)
    private LocalDate admissionDate;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    // Method to set admissionDate before persisting
    @PrePersist
    protected void onCreate() {
        if (admissionDate == null) {
            admissionDate = LocalDate.now(); // Set to the current date if not already set
        }
    }

    // Getters and Setters

    public int getUserid() {
        return this.userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getRoomId() {
        return this.roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public LocalDate getAdmissionDate() {
        return this.admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
}
