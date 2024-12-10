package com.example.campusshuttle.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BusStop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long busStopId;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;

    private int studentsWaiting;

    private int defaultShuttle;
}
