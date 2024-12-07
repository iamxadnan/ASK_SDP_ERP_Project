package com.klef.jfsd.sdpproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "room_number", nullable = false, unique = true)
    private String roomNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false)
    private RoomType roomType;

    @Enumerated(EnumType.STRING)
    @Column(name = "subcategory", nullable = false)
    private RoomSubcategory subcategory;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "available_beds", nullable = false)
    private int availableBeds;

    @Column(name = "price", nullable = false)
    private double price;

    // Enum for room type
    public enum RoomType {
        LUXURY, SUPER_LUXURY, DELUXE
    }

    // Enum for subcategory
    public enum RoomSubcategory {
        THREE_BED_AC, TWO_BED_AC, THREE_BED_NON_AC, TWO_BED_NON_AC, SINGLE_BED_AC, SINGLE_BED_NON_AC
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public RoomSubcategory getSubcategory() {
        return subcategory;
    }

    public void setSubcategory(RoomSubcategory subcategory) {
        this.subcategory = subcategory;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAvailableBeds() {
        return availableBeds;
    }

    public void setAvailableBeds(int availableBeds) {
        this.availableBeds = availableBeds;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}