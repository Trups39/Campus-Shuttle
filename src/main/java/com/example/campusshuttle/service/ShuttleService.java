package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.Shuttle;

public interface ShuttleService {
    Shuttle getShuttleById(Long shuttleId);
    Iterable<Shuttle> getAllShuttles();
    Shuttle saveShuttle(Shuttle shuttle);
    Shuttle updateShuttle(Long shuttleId, Shuttle updatedShuttle);
    void deleteShuttle(Long shuttleId);
    String requestPickup(Long shuttleId, String Suid);
    String addPassenger(Long shuttleId, String Suid);

    Shuttle updateShuttleCurrentLocation(Long shuttleId, Location location);
}