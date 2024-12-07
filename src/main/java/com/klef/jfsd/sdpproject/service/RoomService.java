package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Room;
import java.util.List;

public interface RoomService {
    // Get all available rooms (with available beds)
    List<Room> getAvailableRooms();

    // Get rooms by their type and subcategory
    List<Room> getRoomsByTypeAndSubcategory(Room.RoomType roomType, Room.RoomSubcategory subcategory);

    // Get rooms within a given price range
    List<Room> getRoomsByPriceRange(double minPrice, double maxPrice);

    // Create a new room
    Room createRoom(Room room);

    // Update an existing room by ID
    Room updateRoomById(int roomId, Room updatedRoom);

    // Delete a room by its ID
    void deleteRoom(int roomId);

    // Get all rooms in the system
    List<Room> getAllRooms();
}
