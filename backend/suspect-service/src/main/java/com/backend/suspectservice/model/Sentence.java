/*
 * @ (#) Sentence.java  1.0 7/3/2025
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
@Table(name = "sentence")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE) // Set private level for all fields
public class Sentence {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "sentence_id")
    String sentenceId;
    @Column(name = "case_id", nullable = false)
    String caseId;
    @Column(name = "case_result_id")
    String caseResultId;
    @Column(name = "sentence_type")
    String sentenceType;
    String duration;
    @Column(name = "conditions")
    String condition;
    @Column(name = "sentence_date")
    String sentenceDate;
    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;
}
