package com.backend.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class User {
    @Id
    @Column(name = "user_name")
    String userName;
    @Column(name = "password_hash",nullable = false)
    String passwordHash;
    @Column(name = "full_name")
    String fullName;
    @Column(name = "avatar_url")
    String avatarUrl;
    String email;
    @Column(name = "phone_number")
    String phoneNumber;
    @Column(name = "create_at")
    LocalDateTime createAt;
    @Column(name = "is_delete", nullable = false)
    boolean isDeleted = false;
    @ManyToOne
    @JoinColumn(name = "role_id")
    Role role;
}