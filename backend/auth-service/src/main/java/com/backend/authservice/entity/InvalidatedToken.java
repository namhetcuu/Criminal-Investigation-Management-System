/*
 * @ (#) InvalidToken.java  1.0 7/12/2025
 *
 * Copyright (c) 2025. All rights reserved
 */

package com.backend.authservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

/*
 * @description
 * @author: Khuong Pham
 * @date:   7/12/2025
 * @version:    1.0
 */
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvalidatedToken {
    @Id
    String id;
    Date expiredTime;
}
