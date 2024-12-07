package com.klef.jfsd.sdpproject.service;

import com.klef.jfsd.sdpproject.models.Room;
import com.klef.jfsd.sdpproject.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Override
    public List<Room> getAvailableRooms() {
        return roomRepository.findAvailableRooms();
    }

    @Override
    public List<Room> getRoomsByTypeAndSubcategory(Room.RoomType roomType, Room.RoomSubcategory subcategory) {
        return roomRepository.findByRoomTypeAndSubcategory(roomType, subcategory);
    }

    @Override
    public List<Room> getRoomsByPriceRange(double minPrice, double maxPrice) {
        return roomRepository.findByPriceBetween(minPrice, maxPrice);
    }

    @Override
    public Room createRoom(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public Room updateRoomById(int roomId, Room updatedRoom) {
        Optional<Room> existingRoomOpt = roomRepository.findById(roomId);
        if (existingRoomOpt.isPresent()) {
            Room existingRoom = existingRoomOpt.get();
            // Update fields of the existing room
            existingRoom.setRoomNumber(updatedRoom.getRoomNumber());
            existingRoom.setRoomType(updatedRoom.getRoomType());
            existingRoom.setSubcategory(updatedRoom.getSubcategory());
            existingRoom.setCapacity(updatedRoom.getCapacity());
            existingRoom.setAvailableBeds(updatedRoom.getAvailableBeds());
            existingRoom.setPrice(updatedRoom.getPrice());
            // Save and return the updated room
            return roomRepository.save(existingRoom);
        } else {
            throw new RuntimeException("Room with ID " + roomId + " not found");
        }
    }

    @Override
    @Transactional
    public void deleteRoom(int roomId) {
        roomRepository.deleteById(roomId);
    }

    @Override
    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }
}
