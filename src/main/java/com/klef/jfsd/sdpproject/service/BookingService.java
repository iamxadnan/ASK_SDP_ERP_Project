package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Booking;
import java.util.List;

public interface BookingService {
    // Get all bookings made by a specific hosteler
    List<Booking> getBookingsByHostelerId(int userId);

    // Get all bookings for a specific room
    List<Booking> getBookingsByRoomId(int roomId);

    // Get bookings based on their status
    List<Booking> getBookingsByStatus(String status);

    // Get bookings for a specific hosteler and room
    List<Booking> getBookingsByHostelerAndRoom(int hostelerId, int roomId);

    // Create a new booking
    Booking createBooking(Booking booking);

    // Update an existing booking
    Booking updateBooking(Booking booking);

    // Update the status of an existing booking by its ID
    Booking updateBookingStatus(int bookingId, String status);

    // Delete a booking by its ID
    void deleteBooking(int bookingId);

    // Get all bookings
    List<Booking> getAllBookings();
}
