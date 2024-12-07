package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Booking;
import com.klef.jfsd.sdpproject.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Override
    public List<Booking> getBookingsByHostelerId(int userId) {
        return bookingRepository.findByHosteler_Userid(userId);
    }

    @Override
    public List<Booking> getBookingsByRoomId(int roomId) {
        return bookingRepository.findByRoom_Id(roomId);
    }

    @Override
    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    @Override
    public List<Booking> getBookingsByHostelerAndRoom(int hostelerId, int roomId) {
        return bookingRepository.findBookingsByHostelerAndRoom(hostelerId, roomId);
    }

    @Override
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public Booking updateBookingStatus(int bookingId, String status) {
        Optional<Booking> bookingOpt = bookingRepository.findById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus(status);
            return bookingRepository.save(booking);
        } else {
            throw new RuntimeException("Booking with ID " + bookingId + " not found");
        }
    }

    @Override
    public void deleteBooking(int bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
