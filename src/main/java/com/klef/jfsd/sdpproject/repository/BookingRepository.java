package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.jfsd.sdpproject.models.Booking;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
    
    // In your BookingRepository
    List<Booking> findByHosteler_Userid(int userid);


    // Find all bookings for a specific room
    List<Booking> findByRoom_Id(int roomId);

    // Find bookings by status
    List<Booking> findByStatus(String status);

    // Custom query to find bookings for a specific hosteler and room
    @Query("SELECT b FROM Booking b WHERE b.hosteler.userid = ?1 AND b.room.id = ?2")
    List<Booking> findBookingsByHostelerAndRoom(int hostelerId, int roomId);
}
