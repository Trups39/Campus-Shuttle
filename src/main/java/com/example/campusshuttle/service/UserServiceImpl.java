package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.enums.UserStatus;
import com.example.campusshuttle.repository.LocationRepository;
import com.example.campusshuttle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LocationRepository locationRepository) {
        this.userRepository = userRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public User getUserBySuid(Integer SUId) {
        return userRepository.findBySuid(SUId).orElse(null);
    }

    @Override
    public List<User> getUsersByLocationAndStatus(Location location, UserStatus userStatus) {
        Location existingLocation = locationRepository.findByLatitudeAndLongitude(location.getLatitude(), location.getLongitude());
        if (existingLocation == null) existingLocation = locationRepository.save(location);
        return userRepository.findByAddress_LocationAndStatus(existingLocation, userStatus);
    }
    @Override
    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long userId, User updatedUser) {
        if (userRepository.existsById(userId)) {
            updatedUser.setUserId(userId);
            return userRepository.save(updatedUser);
        }
        return null; // User with given ID not found
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }


    @Override
    public boolean isValidSuiD(Integer Suid) {
        // Check if user with given SUID exists in the database
        Optional<User> userOptional = userRepository.findBySuid(Suid);
        return userOptional.isPresent();
    }
}
