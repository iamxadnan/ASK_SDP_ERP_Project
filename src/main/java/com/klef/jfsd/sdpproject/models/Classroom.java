package com.klef.jfsd.sdpproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "rooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber; 

    @Column(name = "capacity", nullable = false)
    private int capacity; 

    @Column(name = "is_lab", nullable = false)
    private boolean isLab; 

    @Column(name = "description")
    private String description; 

    public Classroom() {}

    public Classroom(String roomNumber, int capacity, boolean isLab, String description) {
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.isLab = isLab;
        this.description = description;
    }

    // Getters and Setters
    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isLab() {
        return isLab;
    }

    public void setLab(boolean lab) {
        isLab = lab;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
