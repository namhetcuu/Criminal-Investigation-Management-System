/*
 * @ (#) Indictment.java  1.0 7/3/2025
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
public class Indictment {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    @Column(name = "indictment_id")
    String indictmentId;
    @Column(name = "indictment_name")
    String prosecutionId;
    String content;
    @Column(name = "issued_at")
    String issuedAt;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted;
}
