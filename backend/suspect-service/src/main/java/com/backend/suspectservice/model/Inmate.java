/*
 * @ (#) Inmate.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@Data
@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) // Set private level for all fields
public class Inmate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "inmate_id")
    private String imageId;
    @Column(name = "name", nullable = false)
    String sentenceId;
    String fullname;
    @Column(name="assigned_facility")
    String assignedFacility;
    @Column(name="start_date")
    String startDate;
    @Column(name="expected_release")
    String expectedRelease;
    @Column(name="health_status")
    String healthStatus;
    String status;
    @Column(name="is_deleted", nullable = false)
    boolean isDeleted = false;

}
