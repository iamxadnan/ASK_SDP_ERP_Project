package com.klef.jfsd.sdpproject.models;

import java.time.LocalDate;

import jakarta.persistence.*;

// Booking Entity
@Entity
@Table(name = "booking")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private Hosteler hosteler;

    @ManyToOne
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "booking_date", nullable = false)
    private LocalDate bookingDate;

    @Column(name = "status", nullable = false)
    private String status; // e.g., Pending, Approved, Rejected

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Hosteler getHosteler() {
        return hosteler;
    }

    public void setHosteler(Hosteler hosteler) {
        this.hosteler = hosteler;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public LocalDate getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDate bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}