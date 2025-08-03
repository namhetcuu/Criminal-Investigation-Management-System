/*
 * @ (#) Suspect.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.model;

import com.backend.commonservice.enums.SuspectStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@Data
//@Table(name = "suspects")
@Entity
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Suspect {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "suspect_id")
    String suspectId;
    @Column(name = "case_id")
    String caseId;
    String fullName;
    String national;
    String gender;
    String dob;
    String identification;
    String phonenumber;
    String description;
    String address;
    @Column(name = "catch_time")
    LocalDateTime catchTime;
    String notes;
    @Enumerated(EnumType.STRING)
    SuspectStatus status = SuspectStatus.NOT_YET_CATCH; // Default status is PENDING
    @Column(name = "mugshot_url")
    String mugshotUrl;
    @Column(name = "fingerprints_hash")
    String fingerprintsHash;
    @Column(name = "health_status")
    String healthStatus;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false; // Default value is false, indicating the suspect is not deleted
}
