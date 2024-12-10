package com.example.campusshuttle.service;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.enums.UserStatus;

import java.util.List;

public interface UserService {
    User getUserById(Long userId);

    User getUserBySuid(Integer SUId);
    List<User> getUsersByLocationAndStatus(Location location, UserStatus userStatus);
    Iterable<User> getAllUsers();
    User saveUser(User user);
    User updateUser(Long userId, User updatedUser);
    void deleteUser(Long userId);

    boolean isValidSuiD(Integer Suid);
}
