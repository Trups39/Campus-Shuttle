package com.example.campusshuttle.repository;

import com.example.campusshuttle.entity.Location;
import com.example.campusshuttle.entity.User;
import com.example.campusshuttle.enums.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findBySuid(Integer suid);

    List<User> findByAddress_LocationAndStatus(Location location, UserStatus status);
}