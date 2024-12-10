package com.example.campusshuttle.controller;

import com.example.campusshuttle.entity.Address;
import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.Shuttle;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.service.AddressService;
import com.example.campusshuttle.service.LocationService;
import com.example.campusshuttle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final AddressService addressService;
    private final LocationService locationService;

    @Autowired
    public UserController(UserService userService, AddressService addressService, LocationService locationService) {
        this.userService = userService;
        this.addressService = addressService;
        this.locationService = locationService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/suid/{suid}")
    public ResponseEntity<User> getUserBySuid(@PathVariable Integer suid) {
        User user = userService.getUserBySuid(suid);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<Iterable<User>> getAllUsers() {
        Iterable<User> users = userService.getAllUsers();
        if (users != null) {
            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


    private User getUserDetailsFromUser(User user){
        Integer suid = user.getSuid();
        String name = user.getName();
        String email = user.getEmail();

        double latitude = user.getAddress().getLocation().getLatitude();
        double longitude = user.getAddress().getLocation().getLongitude();

        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        Location savedLocation = locationService.saveLocation(location);

        Address address = new Address();
        address.setAddress(user.getAddress().getAddress());
        address.setLocation(savedLocation);
        Address savedAddress = addressService.saveAddress(address);

        User saveUser = new User();
        saveUser.setSuid(suid);
        saveUser.setName(name);
        saveUser.setEmail(email);
        saveUser.setAddress(savedAddress);

        return saveUser;
    }
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User user1 = userService.saveUser(getUserDetailsFromUser(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User updatedUser) {
        User user = userService.updateUser(userId, updatedUser);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
