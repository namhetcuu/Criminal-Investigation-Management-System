package com.backend.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Entity
@Table(name = "permission")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode(of = "permissionId")
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "permission_id")
    String permissionId;
    @Column(name = "description", columnDefinition = "TEXT",unique = true,nullable = false)
    String description;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;

    @ManyToMany(mappedBy = "permissions")
    @JsonIgnoreProperties({"permissions"})
    @ToString.Exclude
    Set<Role> roles;
}