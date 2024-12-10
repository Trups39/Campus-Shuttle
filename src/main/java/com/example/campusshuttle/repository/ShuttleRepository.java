package com.example.campusshuttle.repository;

import com.example.campusshuttle.entity.Shuttle;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShuttleRepository extends CrudRepository<Shuttle, Long> {}