package com.example.campusshuttle.controller;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.Shuttle;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.enums.ShuttleStatus;
import com.example.campusshuttle.enums.UserStatus;
import com.example.campusshuttle.service.ShuttleService;
import com.example.campusshuttle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/shuttles")
public class ShuttleController {
    private final ShuttleService shuttleService;

    @Autowired
    public ShuttleController(ShuttleService shuttleService, UserService userService) {
        this.shuttleService = shuttleService;
    }

    @GetMapping("/{shuttleId}")
    public ResponseEntity<Shuttle> getShuttleById(@PathVariable Long shuttleId) {
        Shuttle shuttle = shuttleService.getShuttleById(shuttleId);
        if (shuttle != null) {
            return ResponseEntity.ok(shuttle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<Shuttle>> getAllShuttles() {
        Iterable<Shuttle> shuttles = shuttleService.getAllShuttles();
        if (shuttles != null) {
            return ResponseEntity.ok(shuttles);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<Shuttle> createShuttle(@RequestBody Shuttle shuttle) {
        Shuttle savedShuttle = shuttleService.saveShuttle(shuttle);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShuttle);
    }

    @PutMapping("/{shuttleId}")
    public ResponseEntity<Shuttle>  updateShuttle(@PathVariable Long shuttleId, @RequestBody Shuttle updatedShuttle) {
        Shuttle shuttle = shuttleService.updateShuttle(shuttleId, updatedShuttle);
        if (shuttle != null) {
            return ResponseEntity.ok(shuttle);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{shuttleId}")
    public ResponseEntity<Void> deleteShuttle(@PathVariable Long shuttleId) {
        shuttleService.deleteShuttle(shuttleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/requestPickup")
    public ResponseEntity<String> requestPickup(@RequestParam("suid") String Suid) {
        return ResponseEntity.ok(shuttleService.requestPickup(1L, Suid));
    }

    @PostMapping("/addPassenger")
    public ResponseEntity<String> addPassenger(@RequestParam("suid") String Suid) {
        return ResponseEntity.ok(shuttleService.addPassenger(1L, Suid));
    }


    @PostMapping("/shuttleLocation")
    public ResponseEntity<Shuttle> updateShuttleLocation(@RequestParam("longitude") double longitude, @RequestParam("latitude") double latitude) {
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        return ResponseEntity.ok(shuttleService.updateShuttleCurrentLocation(1L, location));
    }
}

