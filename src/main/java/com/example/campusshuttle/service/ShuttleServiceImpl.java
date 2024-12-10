package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.Shuttle;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.enums.ShuttleStatus;
import com.example.campusshuttle.enums.UserStatus;
import com.example.campusshuttle.repository.ShuttleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShuttleServiceImpl implements ShuttleService {
    private final ShuttleRepository shuttleRepository;
    private final UserService userService;
    private final LocationService locationService;

    @Autowired
    public ShuttleServiceImpl(ShuttleRepository shuttleRepository, LocationService locationService, UserService userService) {
        this.shuttleRepository = shuttleRepository;
        this.locationService = locationService;
        this.userService = userService;
    }

    @Override
    public Shuttle getShuttleById(Long shuttleId) {
        return shuttleRepository.findById(shuttleId).orElse(null);
    }

    @Override
    public Iterable<Shuttle> getAllShuttles() {
        return shuttleRepository.findAll();
    }

    @Override
    public Shuttle saveShuttle(Shuttle shuttle) {
        Location location = locationService.getOrSaveLocation(shuttle.getCurrentLocation());
        shuttle.setCurrentLocation(location);
        return shuttleRepository.save(shuttle);
    }

    @Override
    public Shuttle updateShuttle(Long shuttleId, Shuttle updatedShuttle) {
        if (shuttleRepository.existsById(shuttleId)) {
            Location location = locationService.getOrSaveLocation(updatedShuttle.getCurrentLocation());
            updatedShuttle.setCurrentLocation(location);
            updatedShuttle.setShuttleId(shuttleId);
            return shuttleRepository.save(updatedShuttle);
        }
        return null;
    }

    @Override
    public void deleteShuttle(Long shuttleId) {
        shuttleRepository.deleteById(shuttleId);
    }

    private void updateShuttleWaitTime(Shuttle shuttle){
        int currentCount = shuttle.getCurrentCount();
        int capacity = shuttle.getCapacity();
        int requestCount = shuttle.getRequestCount();

        int eta = 0;
        int numberOfRidesAfterCurrent = requestCount / capacity;
        int returnTime = 5;
        int timeToDropOneStudent = 5;
        int timeForOneRide = (capacity * timeToDropOneStudent) + returnTime;

        int timeForNextRides = numberOfRidesAfterCurrent*timeForOneRide;
        int timeForCurrentRide = (currentCount * timeToDropOneStudent) + returnTime;


        if(shuttle.getShuttleStatus() != ShuttleStatus.REST) eta += timeForCurrentRide;
        eta += timeForNextRides;


        shuttle.setWaitTime(eta);
        shuttleRepository.save(shuttle);
    }


    @Override
    public String requestPickup(Long shuttleId, String suid){
        Integer Suid = Integer.parseInt(suid);
        if(! userService.isValidSuiD(Suid)) return "Fail! No such user found";
        User user = userService.getUserBySuid(Suid);
        if(user.getStatus() == UserStatus.REQUESTED_PICKUP) return "Fail! Pickup already requested!";
        if(user.getStatus() == UserStatus.ONBOARD) return "Fail! User onboard the Shuttle";
        user.setStatus(UserStatus.REQUESTED_PICKUP);
        userService.saveUser(user);

        Shuttle shuttle = getShuttleById(shuttleId);

        List<User> requests = shuttle.getRequests();
        requests.add(user);
        shuttle.setRequests(requests);

        shuttle.setRequestCount(shuttle.getRequestCount()+1);
        int eta = shuttle.getWaitTime();
        updateShuttleWaitTime(shuttle);
        shuttleRepository.save(shuttle);
        return "ETA: " + eta + " minutes";
    }

    @Override
    public String addPassenger(Long shuttleId, String suid){
        Integer Suid = Integer.parseInt(suid);
        String response = "Passenger added successfully";

        Shuttle shuttle = getShuttleById(shuttleId);
        int currentCount = shuttle.getCurrentCount();
        int capacity = shuttle.getCapacity();




        if(shuttle.getCurrentLocation() == locationService.getLocationById(1L)) shuttle.setShuttleStatus(ShuttleStatus.REST);
        if(currentCount+1 > capacity) {
            shuttle.setShuttleStatus(ShuttleStatus.MOTION);
            shuttle.setCurrentLocation(locationService.getLocationById(2L));
            response = "Passenger added successfully! Shuttle left to drop off students!";
            shuttleRepository.save(shuttle);
        }


        if(shuttle.getShuttleStatus() != ShuttleStatus.REST) return "Fail! Shuttle not at BusStop";
        if(currentCount >= capacity) return "Fail! Shuttle capacity full.";




        User user = userService.getUserBySuid(Suid);
        if(user==null) return "Fail! No such user found";
        if(user.getStatus() == UserStatus.INACTIVE) return "Fail! User needs to request pickup first";
        if(user.getStatus() == UserStatus.ONBOARD) return "Fail! User onboard the Shuttle";

        boolean userIsNext = false;
        for (int i = 0; i < capacity-currentCount; i++) {
            if (user == shuttle.getRequests().get(i)) {
                shuttle.getRequests().remove(i);
                userIsNext = true;
                break;
            }
        }

        if(!userIsNext) return "Fail! It is not your turn";


        user.setStatus(UserStatus.ONBOARD);
        userService.saveUser(user);

        currentCount+=1;
        shuttle.setCurrentCount(currentCount);
        shuttle.setRequestCount(shuttle.getRequestCount()-1);




        shuttleRepository.save(shuttle);
        return response;
    }


    @Override
    public Shuttle updateShuttleCurrentLocation(Long shuttleId, Location location){
        Shuttle shuttle = getShuttleById(shuttleId);
        location = locationService.getOrSaveLocation(location);
        if(location == locationService.getLocationById(1L) & shuttle.getCurrentCount()==0)
            shuttle.setShuttleStatus(ShuttleStatus.REST);
        else shuttle.setShuttleStatus(ShuttleStatus.MOTION);



        List<User> users = userService.getUsersByLocationAndStatus(location, UserStatus.ONBOARD);
        for (User user : users){
            user.setStatus(UserStatus.INACTIVE);
            userService.saveUser(user);

            shuttle.setCurrentCount(Math.max(shuttle.getCurrentCount()-1, 0));
        }

        shuttle.setCurrentLocation(location);

        updateShuttleWaitTime(shuttle);

        if(shuttle.getCurrentCount() == 0 & location != locationService.getLocationById(1L) ) updateShuttleCurrentLocation(1L,locationService.getLocationById(1L));
        return shuttleRepository.save(shuttle);
    }
}
