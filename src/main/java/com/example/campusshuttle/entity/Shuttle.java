package com.example.campusshuttle.entity;


import com.example.campusshuttle.enums.ShuttleStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Shuttle {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long shuttleId;

    @NotNull
    @Column(unique=true)
    private String registrationNumber;

    @NotNull
    private int capacity;

    private int requestCount;

    private int currentCount;

    private int waitTime;

    @OneToMany
    private List<User> requests;

    @OneToOne
    private Location currentLocation;

    @Builder.Default
    private ShuttleStatus shuttleStatus = ShuttleStatus.REST;
}
