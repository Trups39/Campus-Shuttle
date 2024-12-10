package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.BusStop;

import java.util.List;

public interface BusStopService {
    BusStop getBusStopById(Long busStopId);
    List<BusStop> getAllBusStops();
    BusStop saveBusStop(BusStop busStop);
    BusStop updateBusStop(Long busStopId, BusStop updatedBusStop);
    void deleteBusStop(Long busStopId);
}
