package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;

public interface LocationService {
    Location getLocationById(Long locationId);
    Iterable<Location> getAllLocations();
    Location saveLocation(Location location);
    Location updateLocation(Long locationId, Location updatedLocation);
    void deleteLocation(Long locationId);
    Location getOrSaveLocation(Location location);
}
