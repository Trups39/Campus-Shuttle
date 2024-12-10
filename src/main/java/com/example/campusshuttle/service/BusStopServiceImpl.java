package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.BusStop;
import com.example.campusshuttle.repository.BusStopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusStopServiceImpl implements BusStopService {
    private final BusStopRepository busStopRepository;

    @Autowired
    public BusStopServiceImpl(BusStopRepository busStopRepository) {
        this.busStopRepository = busStopRepository;
    }

    @Override
    public BusStop getBusStopById(Long busStopId) {
        return busStopRepository.findById(busStopId).orElse(null);
    }

    @Override
    public List<BusStop> getAllBusStops() {
        return (List<BusStop>) busStopRepository.findAll();
    }

    @Override
    public BusStop saveBusStop(BusStop busStop) {
        return busStopRepository.save(busStop);
    }

    @Override
    public BusStop updateBusStop(Long busStopId, BusStop updatedBusStop) {
        if (busStopRepository.existsById(busStopId)) {
            updatedBusStop.setBusStopId(busStopId);
            return busStopRepository.save(updatedBusStop);
        }
        return null; // Bus stop with given ID not found
    }

    @Override
    public void deleteBusStop(Long busStopId) {
        busStopRepository.deleteById(busStopId);
    }
}
