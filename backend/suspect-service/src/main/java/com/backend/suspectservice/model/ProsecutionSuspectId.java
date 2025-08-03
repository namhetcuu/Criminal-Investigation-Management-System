/*
 * @ (#) ProsecutionSuspectId.java  1.0 7/3/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.suspectservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/3/2025
 * @version:    1.0
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProsecutionSuspectId implements Serializable {
    @Column(name = "prosecution_id")
    String prosecutionId;

    @Column(name = "suspect_id")
    String suspectId;
}
