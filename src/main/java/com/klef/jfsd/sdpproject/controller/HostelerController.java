package com.klef.jfsd.sdpproject.controller;

import com.klef.jfsd.sdpproject.models.Hosteler;
import com.klef.jfsd.sdpproject.models.Room;
import com.klef.jfsd.sdpproject.repository.RoomRepository;
import com.klef.jfsd.sdpproject.service.HostelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hosteler")
public class HostelerController {

    @Autowired
    private HostelerService hostelerService;
    @Autowired
    private RoomRepository roomRepository;
    @PostMapping("/create")
    public ResponseEntity<?> createHosteler(@RequestBody Hosteler hosteler) {
        // Validate if the hosteler object is null or the room ID does not exist
        if (hosteler == null || !roomRepository.existsById(hosteler.getRoomId())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid room ID. The room does not exist.");
        }
    
        // Check if the user ID already exists in the hosteler table
        if (hostelerService.existsById(hosteler.getUserid())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("User ID already exists. No action taken.");
        }
        
        // Fetch the room entity
        Room room = roomRepository.findById(hosteler.getRoomId()).orElse(null);
        if (room == null || room.getAvailableBeds() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The room is either unavailable or fully occupied.");
        }
    
        // Reduce the number of available beds
        room.setAvailableBeds(room.getAvailableBeds() - 1);
        roomRepository.save(room);
    
        // Proceed to create the hosteler
        Hosteler createdHosteler = hostelerService.createHosteler(hosteler);
        return ResponseEntity.ok(createdHosteler);
    }
    

}
