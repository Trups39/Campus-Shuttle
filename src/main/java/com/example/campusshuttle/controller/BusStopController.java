package com.example.campusshuttle.controller;

import com.example.campusshuttle.entity.Address;
import com.example.campusshuttle.entity.BusStop;
import com.example.campusshuttle.service.AddressService;
import com.example.campusshuttle.service.BusStopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bus-stops")
public class BusStopController {
    private final BusStopService busStopService;
    private final AddressService addressService;

    @Autowired
    public BusStopController(BusStopService busStopService, AddressService addressService) {
        this.busStopService = busStopService;
        this.addressService = addressService;
    }

    @GetMapping("/{busStopId}")
    public ResponseEntity<BusStop> getBusStopById(@PathVariable Long busStopId) {
        BusStop busStop = busStopService.getBusStopById(busStopId);
        return ResponseEntity.ok(busStop);
    }

    @GetMapping("/")
    public ResponseEntity<List<BusStop>> getAllBusStops() {
        List<BusStop> busStops = busStopService.getAllBusStops();
        return ResponseEntity.ok(busStops);
    }

    @PostMapping("/")
    public ResponseEntity<BusStop> createBusStop(@RequestBody BusStop busStop) {
        if(busStop.getAddress().getAddressId() != null){
            Address address = addressService.getAddressById(busStop.getAddress().getAddressId());
            BusStop bus_stop = new BusStop();
            busStop.setAddress(address);
            return ResponseEntity.status(HttpStatus.CREATED).body(
                    busStopService.saveBusStop(busStop)
            );
        }else {
            BusStop savedBusStop = busStopService.saveBusStop(busStop);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBusStop);
        }
    }

    @PutMapping("/{busStopId}")
    public ResponseEntity<BusStop> updateBusStop(@PathVariable Long busStopId, @RequestBody BusStop updatedBusStop) {
        BusStop busStop = busStopService.updateBusStop(busStopId, updatedBusStop);
        if (busStop != null) {
            return ResponseEntity.ok(busStop);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{busStopId}")
    public ResponseEntity<Void> deleteBusStop(@PathVariable Long busStopId) {
        busStopService.deleteBusStop(busStopId);
        return ResponseEntity.noContent().build();
    }
}
