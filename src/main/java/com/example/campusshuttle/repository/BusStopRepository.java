package com.example.campusshuttle.repository;

import com.example.campusshuttle.entity.BusStop;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusStopRepository extends CrudRepository<BusStop, Long> {}