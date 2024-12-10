package com.example.campusshuttle.entity;

import com.example.campusshuttle.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;




@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Users")
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotNull
    @Digits(integer = 9, fraction = 0, message="Enter a valid SUID")
    private Integer suid;

    @NotBlank(message = "User Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @ManyToOne(cascade = CascadeType.ALL)
    private Address address;

    @Builder.Default
    private UserStatus status = UserStatus.INACTIVE;
}
