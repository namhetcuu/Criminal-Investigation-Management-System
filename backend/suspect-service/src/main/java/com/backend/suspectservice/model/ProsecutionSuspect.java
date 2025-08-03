/*
 * @ (#) Prosecution.java  1.0 7/3/2025
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
@Table(name = "prosecution_suspect")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) // Set private level for all fields
public class ProsecutionSuspect {
    @EmbeddedId
    ProsecutionSuspectId prosecutionSuspectId;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
