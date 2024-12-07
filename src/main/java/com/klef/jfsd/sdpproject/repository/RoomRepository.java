package com.klef.jfsd.sdpproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.klef.jfsd.sdpproject.models.Room;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    // Find rooms based on room type and subcategory
    List<Room> findByRoomTypeAndSubcategory(Room.RoomType roomType, Room.RoomSubcategory subcategory);

    // Find rooms that are currently available
    @Query("SELECT r FROM Room r WHERE r.availableBeds > 0")
    List<Room> findAvailableRooms();

    // Find rooms by price range
    List<Room> findByPriceBetween(double minPrice, double maxPrice);
}
