package com.example.campusshuttle.repository;

import com.example.campusshuttle.entity.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Long> {
    Location findByLatitudeAndLongitude(double latitude, double longitude);
}
