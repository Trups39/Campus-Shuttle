package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location getLocationById(Long locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }

    @Override
    public Iterable<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long locationId, Location updatedLocation) {
        if (locationRepository.existsById(locationId)) {
            updatedLocation.setLocationId(locationId);
            return locationRepository.save(updatedLocation);
        }
        return null;
    }

    @Override
    public void deleteLocation(Long locationId) {
        locationRepository.deleteById(locationId);
    }

    @Override
    public Location getOrSaveLocation(Location location){
        Location elocation = locationRepository.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        if (elocation == null) elocation = locationRepository.save(location);
        return elocation;
    }
}
